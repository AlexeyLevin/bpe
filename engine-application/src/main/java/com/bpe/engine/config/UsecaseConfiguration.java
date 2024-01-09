package com.bpe.engine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bpe.engine.usecase.ProcessConfig;
import com.bpe.engine.usecase.ProcessExecutor;
import com.bpe.engine.usecase.ProcessEventPublisher;
import com.bpe.engine.usecase.execute.ProcessUseCases;
import com.bpe.engine.usecase.ProcessInstanceStepHandler;
import com.bpe.engine.usecase.access.EngineExtractor;
import com.bpe.engine.usecase.access.EnginePersister;

import java.util.Map;

@SuppressWarnings("rawtypes")
@Slf4j
@Configuration
public class UsecaseConfiguration {

    @Bean
    public Map<String, ProcessInstanceStepHandler> stepHandlerMap(ApplicationContext context) {
        var stepHandlerMap = context.getBeansOfType(ProcessInstanceStepHandler.class);
        log.debug("stepHandlerMap configure with {}", stepHandlerMap);
        return stepHandlerMap;
    }

    @Bean
    public ProcessConfig processEngineConfig(ProcessUseCases processUseCases) {
        return processUseCases;
    }

    @Bean
    public ProcessExecutor processEngineExecutor(ProcessUseCases processUseCases) {
        return processUseCases;
    }

    @Bean
    public ProcessEventPublisher processInstanceStepEventPublisher(ProcessUseCases processUseCases) {
        return processUseCases;
    }

    @Bean
    public ProcessUseCases processEngineExecutorUseCases(
            EngineExtractor extractor,
            EnginePersister persister,
            Map<String, ProcessInstanceStepHandler> stepHandlerMap) {
        var processEngine = new ProcessUseCases(extractor, persister, stepHandlerMap);
        stepHandlerMap.values().forEach(processEngine::registerReasonEventHandler);
        log.debug("processEngine configure with {}", processEngine);
        return processEngine;
    }
}
