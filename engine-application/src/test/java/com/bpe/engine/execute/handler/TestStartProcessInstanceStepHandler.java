package com.bpe.engine.execute.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.ProcessInstanceStepHandler;
import com.bpe.engine.usecase.event.StartProcessInstanceStepEvent;

@Slf4j
@Component
public class TestStartProcessInstanceStepHandler implements ProcessInstanceStepHandler<StartProcessInstanceStepEvent> {

    @Override
    public Class<StartProcessInstanceStepEvent> executionReasonEventType() {
        return StartProcessInstanceStepEvent.class;
    }
    
    @Override
    public void execute(ProcessInstanceStep step) {
        log.info("TestStartStepHandler execute");
    }

    @Override
    public void handleException(ProcessInstanceStep step, Exception ex) {
        log.error("TestStartStepHandler exception step {} ex {}", ex);
    }
}
