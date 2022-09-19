package org.simply.api.integrationtest.steps;

import org.simply.api.common.model.Config;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class AllowedWebhookSteps extends BaseSteps {

    @When("^the user post configuration as per (.+) payload to webhook$")
    public void the_user_calls_webhook_config_api(String payloadFilename) throws Exception {

        String fileContent = getFileContent(payloadFilename);

        log.debug("Calling api to update webhook config, payload {}", fileContent);

        Config config = objectMapper.readValue(fileContent, Config.class);

        String endpoint = "/api/webhooks/config";

        ResponseEntity<Config> output = post(endpoint, config, Config.class);

        context().set("webhookConfigStatus", output.getStatusCode());
    }

    @Then("^the user verify status code of (\\d+) for webhook$")
    public void the_user_receives_status_code_of(int statusCode) {

        log.debug("verify status code matches {}", statusCode);

        HttpStatus httpStatus = context().get("webhookConfigStatus");

        Assertions.assertEquals(statusCode, httpStatus.value());
    }

    @And("^the user get configuration as per (.+) json for webhook$")
    public void the_user_get_configuration_for_allowed_webhook(String jsonFilename) throws Exception {

        log.debug("Calling api to get config of webhook.");

        String fileContent = getFileContent(jsonFilename);

        Config config = objectMapper.readValue(fileContent, Config.class);

        String endpoint = "/api/webhooks/config";

        Config configFromServer = get(endpoint, Config.class).getBody();

        Assertions.assertEquals(config.getProcessorDelay(), configFromServer.getProcessorDelay());
        Assertions.assertEquals(config.getControllerDelay(), configFromServer.getControllerDelay());
        Assertions.assertEquals(config.getControllerFail(), configFromServer.getControllerFail());
        Assertions.assertEquals(config.getProcessorFail(), configFromServer.getProcessorFail());
    }
}