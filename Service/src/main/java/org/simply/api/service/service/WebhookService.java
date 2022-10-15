package org.simply.api.service.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.common.model.Config;
import org.simply.api.common.model.Counter;
import org.simply.api.common.model.webhook.WebhookPayload;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class WebhookService {

    @Getter
    private final Config config;

    private final Counter counter;

    @Getter
    private Map<String, WebhookPayload> cache = new ConcurrentHashMap<>();


    public WebhookService(Config config, Counter counter) {
        this.config = config;
        this.counter = counter;
    }

    @Async("webhookAsyncExecutor")
    public void process(WebhookPayload webhookPayload) throws InterruptedException {
        try {
            counter.start();

            log.info("## Start processing - counter: {}, delay: {}, doFail: {}, id: {}", counter.incrementAndGet(), config.getProcessorDelay(), config.getProcessorFail(), webhookPayload.getId());

            if (config.getProcessorDelay() != null) {
                Thread.sleep(config.getProcessorDelay());
            }

            cache.put(webhookPayload.getId(), webhookPayload);

            if (Optional.ofNullable(config.getProcessorFail()).orElse(false)) {
                throw new RuntimeException("Intentional failure at processor level");
            }

            log.info("## End processing - counter: {}, delay: {}, doFail: {}, id: {}", counter.getCounter(), config.getProcessorDelay(), config.getProcessorFail(), webhookPayload.getId());
        } finally {
            counter.end();
        }


    }

    public WebhookPayload get(String key) {
        return cache.get(key);
    }

    public void reset() {
        counter.reset();
        cache = new ConcurrentHashMap<>();
    }

    public Long getDuration() {
        return counter.duration();
    }
}
