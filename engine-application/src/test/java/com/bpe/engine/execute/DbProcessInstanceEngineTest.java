package com.bpe.engine.execute;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.domain.ProcessDefinition;
import com.bpe.engine.execute.config.TestProcessInstanceExecuteWith3StepsConfiguration;
import com.bpe.engine.usecase.event.StartProcessInstanceStepEvent;
import com.bpe.engine.usecase.execute.ProcessUseCases;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestProcessInstanceExecuteWith3StepsConfiguration.class})
class DbProcessInstanceEngineTest {

    @Autowired
    private ProcessUseCases processEngineExecutorUseCases;

    @ParameterizedTest
    @MethodSource("com.bpe.engine.execute.argument.ProcessDefinitionTestArg#startProcessInstanceWith3StartupProcessDefinitionStep")
    public void whenStartProcess_ThenCreateFirstOneStep(ProcessDefinition processDefinitionWithSteps,
                                                         String businessEntityType,
                                                         String businessEntityId) {
        processEngineExecutorUseCases.registerProcessDefinition(processDefinitionWithSteps);
        var uuid = processEngineExecutorUseCases.startProcessInstance(processDefinitionWithSteps.getProcessDefinitionKey(), businessEntityType, businessEntityId);
        List<ProcessInstanceStep> collectionWithFirstStep = processEngineExecutorUseCases.getNewProcessInstanceStepsForExecution(100_500);
        assertEquals(1, collectionWithFirstStep.size());
        assertEquals(processDefinitionWithSteps.getProcessDefinitionKey(), collectionWithFirstStep.get(0).getProcessDefinitionKey());
    }


    @ParameterizedTest
    @MethodSource("com.bpe.engine.execute.argument.ProcessDefinitionTestArg#startProcessInstanceWith3StartupProcessDefinitionStep")
    public void whenStartProcessWith3Steps_ThenProcessFinishAt3Step(ProcessDefinition processDefinitionWithSteps,
                                                                   String businessEntityType,
                                                                   String businessEntityId) {
        processEngineExecutorUseCases.registerProcessDefinition(processDefinitionWithSteps);
//        processEngineExecutorUseCases.startProcessInstance()
        StartProcessInstanceStepEvent startEvent = StartProcessInstanceStepEvent
                .eventBuilder()
                .processInstanceId(UUID.randomUUID())
                .processDefinitionKey(processDefinitionWithSteps.getProcessDefinitionKey())
                .businessEntityType(businessEntityType)
                .businessEntityId(businessEntityId)
                .build();
        processEngineExecutorUseCases.publish(startEvent);
//        var uuid = engine.startProcessInstance(processDefinitionWithSteps.getProcessDefinitionKey(), businessEntityType, businessEntityId);
        List<ProcessInstanceStep> collectionWithFirstStep = processEngineExecutorUseCases.getNewProcessInstanceStepsForExecution(100_500);
        assertEquals(1, collectionWithFirstStep.size());
        processEngineExecutorUseCases.execute(collectionWithFirstStep.get(0));
    }


}