package org.simply.api.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.webhook")
@Getter
@Setter
public class WebhookConfiguration {

    private int corePoolSize;
    private int maxPoolSize;
}