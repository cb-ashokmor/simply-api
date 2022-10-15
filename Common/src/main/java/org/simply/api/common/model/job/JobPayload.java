package org.simply.api.common.model.job;

import lombok.*;
import org.simply.api.common.model.webhook.WebhookPayload;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPayload {

    private int requests;
    private boolean async;
    private WebhookPayload webhookPayload;
}
