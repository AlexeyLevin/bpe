package com.bpe.engine.persistence;

import lombok.RequiredArgsConstructor;
import com.bpe.engine.domain.ProcessDefinition;
import com.bpe.engine.domain.ProcessInstance;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.access.EngineExtractor;
import com.bpe.engine.usecase.access.EnginePersister;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class InMemoryEngineRepository implements EngineExtractor, EnginePersister {

    private final InMemoryProcessDefinitionRepository processDefinitionRepository;
    private final InMemoryProcessInstanceRepository processInstanceRepository;
    private final InMemoryProcessInstanceStepRepository processInstanceStepRepository;

    @Override
    public Optional<ProcessDefinition> getProcessDefinitionByKey(String processDefinitionKey) {
        return processDefinitionRepository.findProcessDefinitionByKey(processDefinitionKey);
    }

    @Override
    public Collection<ProcessInstanceStep> findAllByIsInExecutingIsFalse(Integer limit) {
        return processInstanceStepRepository.findAllByIsInExecutingIsFalse(limit);
    }

    @Override
    public void persistProcessDefinition(ProcessDefinition processDefinition) {
        processDefinitionRepository.save(processDefinition);
    }

    @Override
    public void persistProcessInstance(ProcessInstance processInstance) {
        processInstanceRepository.save(processInstance);
    }

    @Override
    public void persistProcessInstanceStep(ProcessInstanceStep step) {
        processInstanceStepRepository.save(step);
    }
}
