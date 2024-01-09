package com.bpe.engine.usecase.event;

import lombok.*;
import com.bpe.engine.domain.ProcessInstanceStepEvent;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
public class StartProcessInstanceStepEvent extends ProcessInstanceStepEvent {

    private final String businessEntityType;
    private final String businessEntityId;

    @Builder(builderMethodName = "eventBuilder")
    public StartProcessInstanceStepEvent(String processDefinitionKey,
                                         UUID processInstanceId,
                                         String businessEntityType,
                                         String businessEntityId) {
        super(processDefinitionKey, processInstanceId, false);
        this.businessEntityId = businessEntityId;
        this.businessEntityType = businessEntityType;
    }
}
