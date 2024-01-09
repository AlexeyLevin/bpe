package com.bpe.engine.usecase;

import com.bpe.engine.domain.ProcessDefinition;
import com.bpe.engine.domain.ProcessDefinitionStep;

public interface ProcessConfig {

    void registerProcessDefinition(ProcessDefinition processDefinition);

    void registerStepHandler(ProcessDefinitionStep processDefinitionStep,
                             ProcessInstanceStepHandler<?> processInstanceStepHandler);
}
