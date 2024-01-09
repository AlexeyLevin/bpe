package com.bpe.engine.usecase.execute;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.bpe.engine.common.error.Constants;
import com.bpe.engine.domain.*;
import com.bpe.engine.usecase.ProcessExecutor;
import com.bpe.engine.usecase.ProcessConfig;
import com.bpe.engine.usecase.ProcessEventPublisher;
import com.bpe.engine.usecase.ProcessInstanceStepHandler;
import com.bpe.engine.usecase.access.EngineExtractor;
import com.bpe.engine.usecase.access.EnginePersister;
import com.bpe.engine.usecase.handle.DefaultProcessInstanceStepHandler;

import java.util.*;
import java.util.stream.Collectors;

import static com.bpe.engine.domain.ProcessDefinition.*;

@Slf4j
@ToString
@AllArgsConstructor
public class ProcessUseCases implements ProcessExecutor, ProcessConfig, ProcessEventPublisher {

    private final EngineExtractor engineExtractor;
    private final EnginePersister enginePersister;
    private final Map<String, ProcessInstanceStepHandler> stepNameStepHandlerMap;
    private final Map<Class<?>, List<ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent>>> listHashMap = new HashMap<>();

    @Override
    public void registerProcessDefinition(ProcessDefinition processDefinition) {
        enginePersister.persistProcessDefinition(processDefinition);
    }

    @Override
    public void registerStepHandler(ProcessDefinitionStep processDefinitionStep,
                                    ProcessInstanceStepHandler<?> processInstanceStepHandler) {
        stepNameStepHandlerMap.put(processDefinitionStep.getProcessStepDefinitionKey(), processInstanceStepHandler);
    }

    @Override
    public void registerReasonEventHandler(final ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent> handler) {
        Class<? extends ProcessInstanceStepEvent> type = handler.executionReasonEventType();
        List<ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent>> stepHandlers = listHashMap.get(type);
        if (stepHandlers == null) {
            List<ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent>> handlers = new ArrayList<>();
            handlers.add(handler);
            listHashMap.put(type, handlers);
        } else {
            stepHandlers.add(handler);
        }
    }

    @Override
    public UUID startProcessInstance(String processDefinitionKey,
                                     String businessEntityType,
                                     String businessEntityId
    ) {
        var processDefinition = engineExtractor.getProcessDefinitionByKey(processDefinitionKey)
                .orElseThrow();
        var processInstance = ProcessInstance.builder()
                .id(UUID.randomUUID())
                .processDefinitionKey(processDefinition.getProcessDefinitionKey())
                .businessEntityType(processDefinition.getBusinessEntityName())
                .businessEntityId(businessEntityId)
                .build();
        enginePersister.persistProcessInstance(processInstance);
        var firstStep = processDefinition.getFirstProcessDefinitionStep();
        var processInstanceStep = firstStep.createStep();
        processInstanceStep.setProcessInstanceId(processInstance.getId());
        enginePersister.persistProcessInstanceStep(processInstanceStep);
        return processInstance.getId();
    }

    @Override
//    @Transactional
    public List<ProcessInstanceStep> getNewProcessInstanceStepsForExecution(Integer batchSize) {
        return engineExtractor
                .findAllByIsInExecutingIsFalse(batchSize)
                .stream()
                .peek(step -> step.setInExecuting(Boolean.TRUE))
                .peek(enginePersister::persistProcessInstanceStep)
                .collect(Collectors.toList());
    }

    @Override
    public void execute(ProcessInstanceStep step) {
        MDC.put(Constants.PROCESS_DEFINITION_KEY, step.getProcessDefinitionKey());
        MDC.put(Constants.PROCESS_INSTANCE_ID, step.getProcessInstanceId().toString());
        MDC.put(Constants.PROCESS_INSTANCE_STEP_ID, step.getId().toString());
        var handler = stepNameStepHandlerMap.getOrDefault(step.getProcessStepDefinitionKey(), new DefaultProcessInstanceStepHandler());
        var status = ProcessInstanceStep.ProcessInstanceStepStatus.ERROR;
        try {
            handler.execute(step);
            status = ProcessInstanceStep.ProcessInstanceStepStatus.SUCCESS;
        } catch (Exception ex) {
            log.error("Unable to DbProcessStepEngine.execute(): step={}, ex={}", step, ex.toString());
            try {
                handler.handleException(step, ex);
            } catch (Exception exceptionInExceptionHandling) {
                log.error("Unable to DbProcessStepEngine.executeException(): step={}, ex={}", step, exceptionInExceptionHandling.toString());
            }
        } finally {
            completeWithStatus(step, status);
            MDC.clear();
        }
    }

    //    @Transactional
    private void completeWithStatus(ProcessInstanceStep processInstanceStep, ProcessInstanceStep.ProcessInstanceStepStatus status) {
        processInstanceStep.setProcessInstanceStepStatus(status);
        enginePersister.persistProcessInstanceStep(processInstanceStep);
        //getNextStep and insert nextStep, or if it's a final step getProcess and update status to process is completed
    }

    @Override
    public void publish(ProcessInstanceStepEvent event) {
        log.info("Publishing event {}", event);
        List<ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent>> handlers = listHashMap.get(event.getClass());
        log.debug("Publishing event {} with handlers {}", event, handlers);
        if (handlers != null) {
            eventHandling(event, handlers);
        } else {
            log.error("Publishing event {} handlers not found", event);
        }
    }

    private void eventHandling(ProcessInstanceStepEvent event,
                               List<ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent>> stepHandlers) {
        stepHandlers.forEach(handler -> {
            ProcessInstanceStep step = getStepByEvent(event);
            if (event.isAsyncHandling()) {
                log.debug("Saving event {} with handler {}", event, handler);
                enginePersister.persistProcessInstanceStep(step);
            } else {
                log.debug("Executing event {} with handler {}", event, handler);
                handler.execute(step);
            }
        });
    }

    private ProcessInstanceStep getStepByEvent(ProcessInstanceStepEvent event) {
        log.debug("getStepByEvent {}", event);
        ProcessDefinition processDefinition = engineExtractor.getProcessDefinitionByKey(event.getProcessDefinitionKey())
                .orElseThrow(() -> ProcessDefinitionException.builder()
                        .processDefinitionKey(event.getProcessDefinitionKey())
                        .processDefinitionExceptionMessage(ProcessDefinitionExceptionMessage.PROCESS_DEFINITION_NOT_FOUND)
                        .build());
        return processDefinition.getStepByCauseEvent(event);
    }
}
