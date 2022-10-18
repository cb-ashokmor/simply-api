package org.simply.api.integrationtest.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.simply.api.common.model.job.JobPayload;
import org.simply.api.common.model.job.JobResponse;
import org.simply.api.common.model.webhook.WebhookResponse;
import org.springframework.http.ResponseEntity;

@Slf4j
public class JobSteps extends BaseSteps {

    @When("^submit the job as per (.+) payload, async (.+) and (\\d+) requests$")
    public void call_job(String payloadFilename, boolean async, int requests) throws Exception {
        String fileContent = getFileContent(payloadFilename);

        log.debug("Calling api execute job, payloadFilename {}, async: {}, requests: {}", payloadFilename, async, requests);

        JobPayload payload = objectMapper.readValue(fileContent, JobPayload.class);
        payload.setAsync(async);
        payload.setRequests(requests);

        String endpoint = "/api/jobs";

        ResponseEntity<JobResponse> output = post(endpoint, payload, JobResponse.class);

        context().set("syncJobResponse", output.getBody());
    }

    @Then("^verify job made (\\d+) webhook calls and min (.+) and max (.+) seconds to process$")
    public void verify_successful_sync_job(int jobTotal, float min, float max) {
        log.debug("Verify job webhook call matches, jobTotal: {}, min: {}, max: {}", jobTotal, min, max);

        String endpoint = "/api/webhooks/duration";

        ResponseEntity<WebhookResponse> output = get(endpoint, WebhookResponse.class);

        Assertions.assertEquals(jobTotal, output.getBody().getCount());

        String message = "Controller processing time " + output.getBody().getControllerTime() + " should at greater than " + min + " seconds";
        Assertions.assertTrue(output.getBody().getControllerTime() >= min, message);

        message = "Controller processing time " + output.getBody().getControllerTime() + " should not greater than " + max + " seconds";
        Assertions.assertTrue(output.getBody().getControllerTime() <= max, message);
    }
}