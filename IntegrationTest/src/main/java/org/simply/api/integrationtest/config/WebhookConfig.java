package org.simply.api.integrationtest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class WebhookConfig {
    @Value("${app.webhook.host}")
    private String host;

    @Value("${app.webhook.username}")
    private String username;

    @Value("${app.webhook.password}")
    private String password;
}
