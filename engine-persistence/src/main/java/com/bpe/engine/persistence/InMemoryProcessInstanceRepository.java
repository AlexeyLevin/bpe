package com.bpe.engine.persistence;

import com.bpe.engine.domain.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProcessInstanceRepository {

    private final Map<String, ProcessInstance> storage = new HashMap<>();
    public void save(ProcessInstance processInstance) {
        storage.put(processInstance.getId().toString(), processInstance);
    }
}
