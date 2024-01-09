package com.bpe.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstance {
    private UUID id;
    private String processDefinitionKey;
    @Builder.Default
    private ProcessInstanceStatus processInstanceStatus = ProcessInstanceStatus.NEW;
    private String businessEntityType;
    private String businessEntityId;
    @Builder.Default
    private Instant created = Instant.now();
    @Builder.Default
    private Instant updated = Instant.now();
    @Builder.Default
    private List<ProcessInstanceStep> steps = new ArrayList<>();

    public enum ProcessInstanceStatus {
        NEW, PROCESSING, DONE
    }
}
