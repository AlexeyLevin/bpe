package com.bpe.engine.persistence;

import com.bpe.engine.domain.ProcessInstanceStep;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProcessInstanceStepRepository {

    private final Map<String, ProcessInstanceStep> storage = new HashMap<>();
    public Collection<ProcessInstanceStep> findAllByIsInExecutingIsFalse(Integer limit) {
        return storage.values()
                .stream()
                .filter(processInstanceStep -> !processInstanceStep.isInExecuting())
                .collect(Collectors.toList());
    }

    public void save(ProcessInstanceStep processInstanceStep) {
        storage.put(processInstanceStep.getId().toString(), processInstanceStep);
    }
}
