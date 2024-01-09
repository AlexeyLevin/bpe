package com.bpe.engine.usecase.handle;

import lombok.extern.slf4j.Slf4j;
import com.bpe.engine.domain.ProcessDefinitionStep;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.ProcessInstanceStepHandler;
import com.bpe.engine.usecase.event.DefaultProcessInstanceStepEvent;

@Slf4j
public class DefaultProcessInstanceStepHandler implements ProcessInstanceStepHandler<DefaultProcessInstanceStepEvent> {

    @Override
    public Class<DefaultProcessInstanceStepEvent> executionReasonEventType() {
        return DefaultProcessInstanceStepEvent.class;
    }

    @Override
    public void execute(ProcessInstanceStep step) {
        throw ProcessDefinitionStep.ProcessInstanceStepHandlerException.builder()
                .handlerExceptionMessage(ProcessDefinitionStep.HandlerExceptionMessage.STEP_HANDLER_NOT_FOUND)
                .processDefinitionKey(step.getProcessDefinitionKey())
                .processInstanceId(step.getProcessInstanceId().toString())
                .processInstanceStepId(step.getId().toString())
                .details("not found handlers for step [" + step + "]")
                .build();
    }

    @Override
    public void handleException(ProcessInstanceStep step, Exception ex) {
        log.debug("Hello from default handler exceptionally");
    }

}
