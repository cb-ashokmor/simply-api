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
    private final JobConfiguration jobConfiguration;

    @Bean
    public Executor webhookAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(webhookConfiguration.getCorePoolSize());
        executor.setMaxPoolSize(webhookConfiguration.getMaxPoolSize());
        executor.setThreadNamePrefix("WebhookExecutor-");
        executor.initialize();

        log.info("Initialized webhookAsyncExecutor, corePoolSize: {}, maxPoolSize: {}",
                webhookConfiguration.getCorePoolSize(), webhookConfiguration.getMaxPoolSize());
        return executor;
    }

    @Bean
    public Executor jobAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(jobConfiguration.getCorePoolSize());
        executor.setMaxPoolSize(jobConfiguration.getMaxPoolSize());
        executor.setThreadNamePrefix("JobExecutor-");
        executor.initialize();

        log.info("Initialized jobAsyncExecutor, corePoolSize: {}, maxPoolSize: {}",
                jobConfiguration.getCorePoolSize(), jobConfiguration.getMaxPoolSize());
        return executor;
    }
}
