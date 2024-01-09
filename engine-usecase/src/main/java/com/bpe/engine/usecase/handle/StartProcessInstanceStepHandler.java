package com.bpe.engine.usecase.handle;

import lombok.extern.slf4j.Slf4j;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.ProcessInstanceStepHandler;
import com.bpe.engine.usecase.event.StartProcessInstanceStepEvent;

@Slf4j
public class StartProcessInstanceStepHandler implements ProcessInstanceStepHandler<StartProcessInstanceStepEvent> {
    @Override
    public Class<StartProcessInstanceStepEvent> executionReasonEventType() {
        return StartProcessInstanceStepEvent.class;
    }

    @Override
    public void execute(ProcessInstanceStep step) {
        log.info("StartProcessInstanceStepHandler execute");
    }

    @Override
    public void handleException(ProcessInstanceStep step, Exception ex) {
        log.error("StartProcessInstanceStepHandler exception step {} ex {}", ex);
    }
}
