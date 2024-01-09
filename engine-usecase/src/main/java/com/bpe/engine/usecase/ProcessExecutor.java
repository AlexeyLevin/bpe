package com.bpe.engine.usecase;

import com.bpe.engine.domain.ProcessInstanceStep;

import java.util.List;
import java.util.UUID;

public interface ProcessExecutor {

    UUID startProcessInstance(String processDefinitionKey,
                              String businessEntity,
                              String businessEntityId);

    List<ProcessInstanceStep> getNewProcessInstanceStepsForExecution(Integer batchSize);

    void execute(ProcessInstanceStep processInstanceStep);


}
