package com.bpe.engine.usecase.handle;

import lombok.extern.slf4j.Slf4j;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.ProcessInstanceStepHandler;
import com.bpe.engine.usecase.event.EndProcessInstanceStepEvent;
import com.bpe.engine.usecase.event.StartProcessInstanceStepEvent;

@Slf4j
public class EndProcessInstanceStepHandler implements ProcessInstanceStepHandler<EndProcessInstanceStepEvent> {
    @Override
    public Class<EndProcessInstanceStepEvent> executionReasonEventType() {
        return EndProcessInstanceStepEvent.class;
    }

    @Override
    public void execute(ProcessInstanceStep step) {
        log.info("EndProcessInstanceStepEvent execute");
    }

    @Override
    public void handleException(ProcessInstanceStep step, Exception ex) {
        log.error("EndProcessInstanceStepEvent exception step {} ex {}", ex);
    }
}
