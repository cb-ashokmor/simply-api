package org.simply.api.common.model.webhook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookResponse {

    private String message;

    private Integer count;

    private Float controllerTime;

    private Float processorTime;
}