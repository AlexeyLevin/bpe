package com.bpe.engine.usecase.access;

import com.bpe.engine.domain.ProcessDefinition;
import com.bpe.engine.domain.ProcessInstanceStep;

import java.util.Collection;
import java.util.Optional;

public interface EngineExtractor {

    Optional<ProcessDefinition> getProcessDefinitionByKey(String processDefinitionKey);

    Collection<ProcessInstanceStep> findAllByIsInExecutingIsFalse(Integer limit);
}
