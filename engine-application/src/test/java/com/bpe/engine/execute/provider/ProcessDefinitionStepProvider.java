package com.bpe.engine.execute.provider;


import com.bpe.engine.domain.ProcessDefinitionStep;

import java.util.List;

public class ProcessDefinitionStepProvider {
    public static List<ProcessDefinitionStep> get3TestStepsProcessDefinitionStep(String processDefinitionKey) {
        return List.of(ProcessDefinitionStep.builder()
                        .processDefinitionKey(processDefinitionKey)
                        .processStepDefinitionKey("startup")
                        .isStart(true)
                        .isFinal(false)
                        .isAsync(true)
                        .build(),
                ProcessDefinitionStep.builder()
                        .processDefinitionKey(processDefinitionKey)
                        .processStepDefinitionKey("payload")
                        .isStart(false)
                        .isFinal(false)
                        .isAsync(true)
                        .build(),
                ProcessDefinitionStep.builder()
                        .processDefinitionKey(processDefinitionKey)
                        .processStepDefinitionKey("end")
                        .isStart(false)
                        .isFinal(true)
                        .isAsync(true)
                        .build()
        );
    }
}
