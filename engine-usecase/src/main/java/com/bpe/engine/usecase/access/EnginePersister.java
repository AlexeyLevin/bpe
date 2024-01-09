package com.bpe.engine.usecase.access;

import com.bpe.engine.domain.ProcessDefinition;
import com.bpe.engine.domain.ProcessInstance;
import com.bpe.engine.domain.ProcessInstanceStep;

public interface EnginePersister {

    void persistProcessDefinition(ProcessDefinition processDefinition);

    void persistProcessInstance(ProcessInstance processInstance);

    void persistProcessInstanceStep(ProcessInstanceStep step);
}
