package org.simply.api.service.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.common.model.Config;
import org.simply.api.common.model.Counter;
import org.simply.api.common.model.Reset;
import org.simply.api.common.model.webhook.WebhookResponse;
import org.simply.api.service.service.WebhookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secured/webhooks")
@Slf4j
public class SecuredWebhookController extends BaseWebhookController {

    public SecuredWebhookController(WebhookService webhookService, Counter counter) {
        super(webhookService, counter);
    }

    @PostMapping
    public WebhookResponse post(@RequestBody JsonNode payload) throws InterruptedException {
        return super.post(payload);
    }

    @GetMapping("{key}")
    public JsonNode get(@PathVariable String key) {
        return super.get(key);
    }

    @PostMapping
    @RequestMapping("reset")
    public Reset reset() {
        return super.reset();
    }

    @GetMapping("duration")
    public WebhookResponse getDuration() {
        return super.getDuration();
    }

    @PostMapping("config")
    public Config setConfig(@RequestBody Config config) {

        return super.setConfig(config);
    }

    @GetMapping("config")
    public Config getConfig() {

        return super.getConfig();
    }
}
