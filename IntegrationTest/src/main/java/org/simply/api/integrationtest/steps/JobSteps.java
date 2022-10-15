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

    @When("^the user submit the job as per (.+) payload$")
    public void call_job(String payloadFilename) throws Exception {
        String fileContent = getFileContent(payloadFilename);

        log.debug("Calling api execute job, payload {}", fileContent);

        JobPayload payload = objectMapper.readValue(fileContent, JobPayload.class);

        String endpoint = "/api/jobs";

        ResponseEntity<JobResponse> output = post(endpoint, payload, JobResponse.class);

        context().set("syncJobResponse", output.getBody());
    }


    @Then("^the user verify job made (\\d+) webhook calls$")
    public void verify_successful_sync_job(int jobTotal) {
        log.debug("verify job webhook call matches - {}", jobTotal);

        String endpoint = "/api/webhooks/duration";

        ResponseEntity<WebhookResponse> output = get(endpoint, WebhookResponse.class);

        Assertions.assertEquals(jobTotal, output.getBody().getCount());
    }
}