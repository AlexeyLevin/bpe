package com.bpe.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceStep {
    private UUID id;
    private String processStepDefinitionKey;
    private String processDefinitionKey;
    private UUID processInstanceId;
    @Builder.Default
    private ProcessInstanceStepStatus processInstanceStepStatus = ProcessInstanceStepStatus.NEW;
    private Instant created;
    private Instant updated;
    private boolean isInExecuting;
    private boolean isStart;
    private boolean isFinal;
    private Integer maxExecuteTimeMs;

    public enum ProcessInstanceStepStatus {
        NEW, WAITING, SUCCESS, ERROR
    }
}
