package org.simply.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Config {

    private Integer controllerDelay;

    private Integer processorDelay;

    private Boolean controllerFail;

    private Boolean processorFail;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
