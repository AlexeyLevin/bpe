package com.bpe.engine.usecase.event;

import lombok.Getter;
import lombok.ToString;
import com.bpe.engine.domain.ProcessInstanceStepEvent;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
public class DefaultProcessInstanceStepEvent extends ProcessInstanceStepEvent {
    public DefaultProcessInstanceStepEvent(String processDefinitionKey, UUID processInstanceId) {
        super(processDefinitionKey, processInstanceId, false);
    }
}
