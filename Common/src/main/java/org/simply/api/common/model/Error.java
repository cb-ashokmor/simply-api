package org.simply.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private String error;

    @Override
    public String toString() throws RuntimeException {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
