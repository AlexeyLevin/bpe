package com.bpe.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bpe.engine.persistence.InMemoryEngineRepository;
import com.bpe.engine.persistence.InMemoryProcessDefinitionRepository;
import com.bpe.engine.persistence.InMemoryProcessInstanceRepository;
import com.bpe.engine.persistence.InMemoryProcessInstanceStepRepository;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public InMemoryProcessDefinitionRepository processDefinitionRepository() {
        return new InMemoryProcessDefinitionRepository();
    }

    @Bean
    public InMemoryProcessInstanceRepository processInstanceRepository() {
        return new InMemoryProcessInstanceRepository();
    }

    @Bean
    public InMemoryProcessInstanceStepRepository processInstanceStepRepository() {
        return new InMemoryProcessInstanceStepRepository();
    }

    @Bean
    public InMemoryEngineRepository engineExtractor() {
        return new InMemoryEngineRepository(
                processDefinitionRepository(),
                processInstanceRepository(),
                processInstanceStepRepository());
    }
}
