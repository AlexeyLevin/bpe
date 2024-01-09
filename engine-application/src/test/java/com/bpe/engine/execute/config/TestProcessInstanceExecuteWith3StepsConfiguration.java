package com.bpe.engine.execute.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import com.bpe.engine.config.UsecaseConfiguration;
import com.bpe.engine.execute.handler.TestStartProcessInstanceStepHandler;
import com.bpe.engine.persistence.InMemoryEngineRepository;
import com.bpe.engine.persistence.InMemoryProcessDefinitionRepository;
import com.bpe.engine.persistence.InMemoryProcessInstanceRepository;
import com.bpe.engine.persistence.InMemoryProcessInstanceStepRepository;

import static org.mockito.Mockito.mock;

@Import({
        UsecaseConfiguration.class
})
@TestConfiguration
@ActiveProfiles("test")
public class TestProcessInstanceExecuteWith3StepsConfiguration {

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

    @Bean
    public TestStartProcessInstanceStepHandler testStartProcessInstanceStepHandler() {
        return new TestStartProcessInstanceStepHandler();
    }
}
