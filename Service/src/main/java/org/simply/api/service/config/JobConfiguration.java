package org.simply.api.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.job")
@Getter
@Setter
public class JobConfiguration {

    private ExecutorConfig executor;

    private String webhookHost;

    public int getCorePoolSize() {
        return executor.getCorePoolSize();
    }

    public int getMaxPoolSize() {
        return executor.getMaxPoolSize();
    }
}