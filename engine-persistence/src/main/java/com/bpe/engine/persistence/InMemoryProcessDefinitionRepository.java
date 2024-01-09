package com.bpe.engine.persistence;

import com.bpe.engine.domain.ProcessDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProcessDefinitionRepository {

    private final Map<String, ProcessDefinition> storage = new HashMap<>();
    public Optional<ProcessDefinition> findProcessDefinitionByKey(String processDefinitionKey) {
        return Optional.ofNullable(storage.get(processDefinitionKey));
    }

    public void save(ProcessDefinition processDefinition) {
        storage.put(processDefinition.getProcessDefinitionKey(), processDefinition);
    }
}
