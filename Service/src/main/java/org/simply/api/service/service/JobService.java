package org.simply.api.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.client.HttpService;
import org.simply.api.common.model.job.AsyncJobProcessResult;
import org.simply.api.common.model.job.JobPayload;
import org.simply.api.service.config.JobConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class JobService {
    private final AsyncJobProcessor asyncJobProcessor;

    private final ObjectMapper objectMapper;

    private final HttpService httpService;

    public JobService(JobConfiguration jobConfiguration, AsyncJobProcessor asyncJobProcessor) {
        this.asyncJobProcessor = asyncJobProcessor;
        this.httpService = new HttpService(jobConfiguration.getWebhookHost());
        this.objectMapper = new ObjectMapper();
    }

    public int process(JobPayload payload) {
        log.info("### Start job processing");

        int count = 0;
        ObjectNode content = objectMapper.createObjectNode();
        for (int id = 1; id <= payload.getRequests(); id++) {
            content.put("id", id);
            content.replace("content", payload.getWebhookPayload().getContent());

            ResponseEntity<String> response = httpService.post("/api/webhooks", content, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ++count;
            } else {
                log.warn("{} request is not successful", id);
            }
        }

        log.info("### End job processing");
        return count;
    }

    public int parallelProcess(JobPayload payload) {
        log.info("### Start job parallel processing");

        List<Future<AsyncJobProcessResult>> futureList = new ArrayList<>();

        for (int id = 1; id <= payload.getRequests(); id++) {
            ObjectNode content = objectMapper.createObjectNode();
            content.put("id", id);
            content.replace("content", payload.getWebhookPayload().getContent());

            futureList.add(asyncJobProcessor.process(content, id));
        }

        int count = 0;
        for (Future<AsyncJobProcessResult> future : futureList) {
            try {
                AsyncJobProcessResult result = future.get();
                if (result.getResponseEntity().getStatusCode() == HttpStatus.OK) {
                    ++count;
                } else {
                    log.warn("{} request is not successful", result.getId());
                }
            } catch (ExecutionException | InterruptedException e) {
                log.error("Could not process async job", e);
            }
        }

        log.info("### End job parallel processing");

        return count;
    }
}
