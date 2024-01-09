package com.bpe.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;



@Getter
@SuperBuilder
@AllArgsConstructor
public class ProcessInstanceStepEvent {
    private final UUID id;
    private final String processDefinitionKey;
    private final UUID processInstanceId;
    private final Instant created;
    private final boolean isAsyncHandling;

    protected ProcessInstanceStepEvent(String processDefinitionKey, UUID processInstanceId, boolean isAsyncHandling) {
        this.id = UUID.randomUUID();
        this.isAsyncHandling = isAsyncHandling;
        this.processDefinitionKey = processDefinitionKey;
        this.processInstanceId = processInstanceId;
        this.created = Instant.now();
    }

}