package com.bpe.engine.usecase;

import com.bpe.engine.domain.ProcessInstanceStep;

public interface ProcessInstanceStepHandler<T> {

    /**
     * Type of event that called the step handler
     */
    Class<T> executionReasonEventType();

    void execute(ProcessInstanceStep step);

    void handleException(ProcessInstanceStep step, Exception ex);

    default String getProcessStepDefinitionKey() {
        var result = this.getClass().getSimpleName();
        return result.substring(0, 1).toLowerCase() + result.substring(1);
    }
}
