package org.simply.api.service.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.common.model.Error;
import org.simply.api.common.model.*;
import org.simply.api.service.exception.NoObjectFoundException;
import org.simply.api.service.service.WebhookService;

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

            Payload payload = Payload.builder().content(content).build();

            log.info("Start controller - counter: {}, delay: {}, doFail: {}, id: {}",
                    counter.incrementAndGet(), webhookService.getConfig().getControllerDelay(), webhookService.getConfig().getControllerFail(), payload.getId());

            webhookService.process(payload);

            if (webhookService.getConfig().getControllerDelay() != null) {
                Thread.sleep(webhookService.getConfig().getControllerDelay());
            }

            if (Optional.ofNullable(webhookService.getConfig().getControllerFail()).orElse(false)) {
                throw new RuntimeException("Intentional failure at controller level");
            }

            WebhookResponse response = WebhookResponse.builder().message("Acknowledged").build();

            log.info("End controller - counter: {}, delay: {}, doFail: {}, id: {}",
                    counter.getCounter(), webhookService.getConfig().getControllerDelay(), webhookService.getConfig().getControllerFail(), payload.getId());

            return response;
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
                .controllerTime((float)counter.duration() / 1000)
                .processorTime((float)webhookService.getDuration() / 1000)
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
