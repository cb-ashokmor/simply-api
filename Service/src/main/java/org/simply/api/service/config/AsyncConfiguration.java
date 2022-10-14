package org.simply.api.service.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@AllArgsConstructor
@Slf4j
public class AsyncConfiguration {

    private final WebhookConfiguration webhookConfiguration;

    @Bean(name = "webhookAsyncExecutor")
    public Executor getWebhookAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(webhookConfiguration.getCorePoolSize());
        executor.setMaxPoolSize(webhookConfiguration.getMaxPoolSize());
        executor.initialize();

        log.info("Initialized WebhookAsyncExecutor, corePoolSize: {}, maxPoolSize: {}",
                webhookConfiguration.getCorePoolSize(), webhookConfiguration.getMaxPoolSize());
        return executor;
    }
}
