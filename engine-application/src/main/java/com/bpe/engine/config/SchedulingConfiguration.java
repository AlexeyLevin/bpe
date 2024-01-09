package com.bpe.engine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bpe.engine.execute.ProcessInstanceStepExecutor;
import com.bpe.engine.execute.ProcessInstanceStepScheduler;
import com.bpe.engine.usecase.ProcessExecutor;

@Slf4j
@Configuration
public class SchedulingConfiguration {

    @Value("${orchestrator.step.execution.scheduler.batch.size}")
    private Integer stepExecutionSchedulerBatchSize;

    @Bean
    public ProcessInstanceStepScheduler processStepScheduler(ProcessExecutor processEngineExecutor,
                                                             ProcessInstanceStepExecutor executor) {
        var scheduler = new ProcessInstanceStepScheduler(stepExecutionSchedulerBatchSize, processEngineExecutor, executor);
        log.debug("processStepScheduler configure with {}", scheduler);
        return scheduler;
    }
}
