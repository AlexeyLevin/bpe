package com.bpe.engine.execute;


import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.ProcessExecutor;

import java.util.List;


@Slf4j
@Component
@ToString
@RequiredArgsConstructor
public class ProcessInstanceStepScheduler {

    private final Integer batchSize;
    private final ProcessExecutor processExecutor;

    private final ProcessInstanceStepExecutor executor;

    @Scheduled(fixedDelayString = "${orchestrator.step.execution.scheduler.delay}")
    public void pushingNewProcessInstanceStepsToExecute() {
        if (executor.isNotOverload()) {
            List<ProcessInstanceStep> stepsForExecution = processExecutor.getNewProcessInstanceStepsForExecution(batchSize);
            if (isNotEmpty(stepsForExecution)) {
                log.info("Executing steps: {}", stepsForExecution);
                executor.execute(stepsForExecution);
            } else {
                log.debug("Skip scheduling, no steps for executing");
            }
        } else {
            log.debug("Skip scheduling, step executor is overload, steps in execution: {}, execution limit {}",
                    executor.getStepsCountIsInExecuting(), executor.getStepExecutionQueueLimit());
        }
    }

    private static boolean isNotEmpty(List<ProcessInstanceStep> stepsForExecution) {
        return stepsForExecution != null && !stepsForExecution.isEmpty();
    }
}
