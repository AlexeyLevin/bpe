package com.bpe.engine.domain;

import lombok.*;
import com.bpe.engine.common.error.EngineError;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDefinitionStep {

    private String processDefinitionKey;
    private String processStepDefinitionKey;
    private boolean isAsync;
    private boolean isStart;
    private boolean isFinal;
    private int maxExecuteTimeMs;
    public ProcessInstanceStep createStep() {
        return ProcessInstanceStep.builder()
                .id(UUID.randomUUID())
                .processDefinitionKey(processDefinitionKey)
                .processStepDefinitionKey(processStepDefinitionKey)
                .processInstanceStepStatus(ProcessInstanceStep.ProcessInstanceStepStatus.NEW)
                .isStart(isStart)
                .isFinal(isFinal)
                .maxExecuteTimeMs(maxExecuteTimeMs)
                .created(Instant.now())
                .updated(Instant.now())
                .build();
    }

    @ToString
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessInstanceStepHandlerException extends RuntimeException implements EngineError {
        private String processDefinitionKey;
        private String processInstanceId;
        private String processInstanceStepId;
        private HandlerExceptionMessage handlerExceptionMessage;
        private String details;
    }
    @Getter
    public enum HandlerExceptionMessage {
        INTERNAL_SERVER_ERROR,
        STEP_NOT_FOUND,
        PROCESS_NOT_FOUND,
        STEP_HANDLER_NOT_FOUND,
        PROCESSING_STEP_NOT_FOUND,
    }
}
