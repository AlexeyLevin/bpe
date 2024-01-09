package com.bpe.engine.execute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.bpe.engine.domain.ProcessInstanceStep;
import com.bpe.engine.usecase.ProcessExecutor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@ToString
@RequiredArgsConstructor
public class ProcessInstanceStepExecutor {

    @Getter
    private final Integer stepExecutionQueueLimit;
    private final Integer stepExecutionQueueOverloadDiff;
    private final Set<UUID> stepIdsInExecution = new ConcurrentSkipListSet<>();
    private final ProcessExecutor processExecutor;
    private final ThreadPoolTaskExecutor processInstanceStepThreadPoolExecutor;

    public boolean isNotOverload() {
        return stepExecutionQueueLimit - getStepsCountIsInExecuting() > stepExecutionQueueOverloadDiff;
    }

    public Integer getStepsCountIsInExecuting() {
        return processInstanceStepThreadPoolExecutor.getQueueSize();
    }

    public void execute(List<ProcessInstanceStep> stepsForExecution) {
        for (ProcessInstanceStep step : stepsForExecution) {
            processInstanceStepThreadPoolExecutor.execute(() -> executeStep(step));
        }
    }

    private void executeStep(ProcessInstanceStep step) {
        try {
            if (isInExecuting(step)) {
                log.debug("Step is already in execution: {}", step);
                return;
            }
            markStepIsInExecution(step);
            log.debug("Step is starting execution: {}", step);
            processExecutor.execute(step);
        } finally {
            unmarkStepIsInExecution(step);
            log.debug("Step is executed: {}", step);
        }
    }

    private boolean isInExecuting(ProcessInstanceStep step) {
        return stepIdsInExecution.contains(step.getId());
    }
    private void markStepIsInExecution(ProcessInstanceStep step) {
        stepIdsInExecution.add(step.getId());
    }

    private void unmarkStepIsInExecution(ProcessInstanceStep step) {
        stepIdsInExecution.remove(step.getId());
    }
}
