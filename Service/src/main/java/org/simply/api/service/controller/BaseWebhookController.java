package org.simply.api.service.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.common.model.*;
import org.simply.api.service.exception.NoObjectFoundException;
import org.simply.api.common.model.*;
import org.simply.api.service.service.WebhookService;
import org.simply.api.common.model.Error;

import java.util.Optional;


@Slf4j
public abstract class BaseWebhookController {
    private final WebhookService webhookService;

    private final Counter counter;

    public BaseWebhookController(WebhookService webhookService, Counter counter) {
        this.webhookService = webhookService;
        this.counter = counter;
    }

    protected WebhookResponse post(JsonNode content) throws InterruptedException {
        try {
            counter.start();

            log.info("Counter: {}, Delay: {}, Fail: {}", counter.incrementAndGet(), webhookService.getConfig().getControllerDelay(), webhookService.getConfig().getControllerFail());

            webhookService.process(Payload.builder().content(content).build());

            if (webhookService.getConfig().getControllerDelay() != null) {
                Thread.sleep(webhookService.getConfig().getControllerDelay());
            }

            if (Optional.ofNullable(webhookService.getConfig().getControllerFail()).orElse(false)) {
                throw new RuntimeException("intentional failure");
            }

            return WebhookResponse.builder().message("Acknowledge").build();
        } finally {
            counter.end();
        }
    }

    public JsonNode get(String key) {
        Payload payload = webhookService.get(key);

        if (java.util.Objects.isNull(payload)) {
            Error error = Error.builder().error("Not found webhook payload for " + key + " key").build();
            throw NoObjectFoundException.builder().error(error).build();
        }

        return payload.getContent();
    }

    public Reset reset() {
        counter.reset();

        webhookService.reset();

        log.info("reset");

        return Reset.builder().message("All data reset").build();
    }

    public WebhookResponse getDuration() {
        return WebhookResponse.builder()
                .controllerTime(counter.duration())
                .processorTime(webhookService.getDuration())
                .count(webhookService.getCache().size()).build();
    }

    public Config setConfig(Config config) {

        webhookService.getConfig().setProcessorDelay(config.getProcessorDelay());
        webhookService.getConfig().setControllerDelay(config.getControllerDelay());
        webhookService.getConfig().setProcessorFail(config.getProcessorFail());
        webhookService.getConfig().setControllerFail(config.getControllerFail());

        log.info("Config set - {}", config);

        return webhookService.getConfig();
    }

    public Config getConfig() {

        return webhookService.getConfig();
    }
}
