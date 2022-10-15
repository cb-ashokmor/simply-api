package org.simply.api.integrationtest.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.simply.api.common.model.Config;
import org.simply.api.common.model.Reset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class SecuredWebhookSteps extends BaseSteps {

    @When("^the user reset the secured webhook stats$")
    public void call_reset_api() {
        log.debug("Calling api reset secured webhook stats");

        String endpoint = "/api/secured/webhooks/reset";

        post(endpoint, null, Reset.class);
    }

    @When("^the user set the configuration as per (.+) payload to secured webhook$")
    public void call_config_api(String payloadFilename) throws Exception {

        String fileContent = getFileContent(payloadFilename);

        log.debug("Calling api to update secured webhook config, payload {}", fileContent);

        Config config = objectMapper.readValue(fileContent, Config.class);

        String endpoint = "/api/secured/webhooks/config";

        ResponseEntity<Config> output = post(endpoint, config, Config.class, true);

        context().set("webhookConfigStatus", output.getStatusCode());
    }

    @Then("^the user verify status code of (\\d+) for secured webhook$")
    public void verify_status_code(int statusCode) {
        log.debug("verify status code matches {}", statusCode);

        HttpStatus httpStatus = context().get("webhookConfigStatus");

        Assertions.assertEquals(statusCode, httpStatus.value());
    }

    @And("^the user get configuration as per (.+) json for secured webhook$")
    public void get_configuration(String jsonFilename) throws Exception {
        log.debug("Calling api to get config of secured webhook.");

        String fileContent = getFileContent(jsonFilename);

        Config config = objectMapper.readValue(fileContent, Config.class);

        String endpoint = "/api/secured/webhooks/config";

        Config configFromServer = get(endpoint, Config.class, true).getBody();

        Assertions.assertEquals(config.getProcessorDelay(), configFromServer.getProcessorDelay());
        Assertions.assertEquals(config.getControllerDelay(), configFromServer.getControllerDelay());
        Assertions.assertEquals(config.getControllerFail(), configFromServer.getControllerFail());
        Assertions.assertEquals(config.getProcessorFail(), configFromServer.getProcessorFail());
    }
}