package com.bpe.engine.execute.provider;

import com.bpe.engine.domain.ProcessDefinition;

public class ProcessDefinitionProvider {
    public static ProcessDefinition getFirstTestProcessDefinition() {
        return ProcessDefinition.builder()
                .processDefinitionKey("paymentProcessV1")
                .description("new payment process definition")
                .businessEntityName("paymentDocument")
                .build();
    }
}
