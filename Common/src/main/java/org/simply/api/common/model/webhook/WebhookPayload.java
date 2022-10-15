package org.simply.api.common.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookPayload {

    private JsonNode content;

    @JsonIgnore
    private String id;

    @JsonIgnore
    public String getId() {

        if (id == null && content != null) {
            JsonNode value = content.findValue("id");
            if (value != null) {
                id = value.asText();
            }
        }
        return id;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
