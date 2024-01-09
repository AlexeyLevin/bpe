package com.bpe.engine.domain;


import lombok.*;
import com.bpe.engine.common.error.EngineError;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDefinition {
    private String processDefinitionKey;
    private String description;
    private String businessEntityName;
    private final List<ProcessDefinitionStep> processDefinitionSteps = new ArrayList<>();

    public ProcessDefinitionStep getFirstProcessDefinitionStep() {
        return processDefinitionSteps.stream().filter(ProcessDefinitionStep::isStart).findFirst()
                .orElseThrow(() -> ProcessDefinitionException.builder()
                        .processDefinitionKey(processDefinitionKey)
                        .processDefinitionExceptionMessage(ProcessDefinitionExceptionMessage.FIRST_STEP_NOT_FOUND)
                        .build());
    }

    public List<ProcessDefinitionStep> addSteps(List<ProcessDefinitionStep> processDefinitionSteps) {
        this.processDefinitionSteps.addAll(processDefinitionSteps);
        return processDefinitionSteps;
    }

    public List<ProcessDefinitionStep> addStep(ProcessDefinitionStep processStep) {
        this.processDefinitionSteps.add(processStep);
        return processDefinitionSteps;
    }

    public ProcessInstanceStep getStepByCauseEvent(ProcessInstanceStepEvent event) {
//        processDefinitionSteps.stream().filter(processDefinitionStep -> processDefinitionStep.)
        return ProcessInstanceStep.builder().build();
    }

    @ToString
    @Builder
    public static class ProcessDefinitionException extends RuntimeException implements EngineError {
        private String processDefinitionKey;
        private ProcessDefinitionExceptionMessage processDefinitionExceptionMessage;
    }

    public enum ProcessDefinitionExceptionMessage {
        INTERNAL_SERVER_ERROR,
        FIRST_STEP_NOT_FOUND,
        PROCESS_DEFINITION_NOT_FOUND
    }

}
