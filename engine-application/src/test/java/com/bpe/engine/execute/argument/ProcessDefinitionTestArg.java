package com.bpe.engine.execute.argument;

import com.bpe.engine.execute.provider.ProcessDefinitionProvider;
import com.bpe.engine.execute.provider.ProcessDefinitionStepProvider;
import com.bpe.engine.domain.ProcessDefinition;
import com.bpe.engine.domain.ProcessDefinitionStep;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class ProcessDefinitionTestArg {
    public static Stream<Arguments> startProcessInstanceWith3StartupProcessDefinitionStep() {
        ProcessDefinition processDefinition = ProcessDefinitionProvider.getFirstTestProcessDefinition();
        List<ProcessDefinitionStep> steps = ProcessDefinitionStepProvider.get3TestStepsProcessDefinitionStep(processDefinition.getProcessDefinitionKey());
        processDefinition.addSteps(steps);
        return Stream.of(Arguments.of(
                processDefinition,
                processDefinition.getBusinessEntityName(),
                "newPaymentDocBlaBlaBla128312837912"
        ));
    }
}
