package com.bpe.engine.usecase;


import com.bpe.engine.domain.ProcessInstanceStepEvent;

public interface ProcessEventPublisher {
    void registerReasonEventHandler(ProcessInstanceStepHandler<? extends ProcessInstanceStepEvent> handler);

    void publish(ProcessInstanceStepEvent event);
}
