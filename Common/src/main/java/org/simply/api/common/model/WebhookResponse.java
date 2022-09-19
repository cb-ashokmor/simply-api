package org.simply.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookResponse {

    private String message;

    private Integer count;

    private Long controllerTime;

    private Long processorTime;
}