package com.bpe.engine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.bpe.engine.execute.ProcessInstanceStepExecutor;
import com.bpe.engine.usecase.ProcessExecutor;

@Slf4j
@Configuration
public class ExecutorConfiguration {

    public static final String THREAD_NAME_PREFIX = "ProcessInstanceStepExecutor-";
    public static final String THREAD_POOL_EXECUTOR_BEAN = "processInstanceStepThreadPoolExecutor";

    @Value("${orchestrator.step.execution.pool.size}")
    private Integer stepExecutionPoolSize;

    @Value("${orchestrator.step.execution.queue.limit}")
    private Integer stepExecutionQueueLimit;

    @Value("${orchestrator.step.execution.queue.overload.diff}")
    private Integer stepExecutionQueueOverloadDiff;

    @Bean(THREAD_POOL_EXECUTOR_BEAN)
    public ThreadPoolTaskExecutor processInstanceStepThreadPoolExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(stepExecutionPoolSize);
        executor.setMaxPoolSize(stepExecutionPoolSize);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();
        log.debug("processStepInstanceScheduler configure with {}", executor);
        return executor;
    }

    @Bean
    public ProcessInstanceStepExecutor processInstanceStepExecutor(@Qualifier("processEngineExecutor") ProcessExecutor processExecutor,
                                                                   ThreadPoolTaskExecutor processInstanceStepThreadPoolExecutor) {
        var executor = new ProcessInstanceStepExecutor(
                stepExecutionQueueLimit, stepExecutionQueueOverloadDiff, processExecutor, processInstanceStepThreadPoolExecutor);
        log.debug("processStepExecutor configure with {}", executor);
        return executor;
    }

}
