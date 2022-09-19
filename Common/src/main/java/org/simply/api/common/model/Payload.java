package org.simply.api.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Payload {

    private JsonNode content;

    @JsonIgnore
    public String getId() {

        if (content != null) {
            JsonNode value = content.findValue("id");
            if (value != null) {
                return value.asText();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
