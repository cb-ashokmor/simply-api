package org.simply.api.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simply.api.common.model.Config;
import org.simply.api.common.model.Counter;
import org.simply.api.common.model.webhook.WebhookPayload;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebhookServiceTest {

    private WebhookService target;

    @BeforeEach
    public void setup() {
        Config config = new Config();
        Counter counter = new Counter();
        target = new WebhookService(config, counter);
    }

    @Test
    public void testDelayProcessing() throws Exception {
        target.getConfig().setProcessorDelay(10);

        assertTrue(target.getDuration() < 10, "Initial delay should be less then a 10 milli");

        WebhookPayload webhookPayload = buildPayload("{\"id\": 1}");
        target.process(webhookPayload);

        assertTrue(target.getDuration() >= 10, "Delay should be more then a 10 milli");
    }


    private WebhookPayload buildPayload(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(content, JsonNode.class);
        return WebhookPayload.builder().content(node).build();
    }
}
