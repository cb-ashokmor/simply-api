package org.simply.api.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.client.HttpService;
import org.simply.api.common.model.job.AsyncJobProcessResult;
import org.simply.api.service.config.JobConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncJobProcessor {

    private final HttpService httpService;

    public AsyncJobProcessor(JobConfiguration jobConfiguration) {
        this.httpService = new HttpService(jobConfiguration.getWebhookHost());
    }

    @Async("jobAsyncExecutor")
    public Future<AsyncJobProcessResult> process(final JsonNode content, int id) {
        log.info("### AsyncJobProcessor - requestId: {}", id);

        ResponseEntity<String> response = httpService.post("/api/webhooks", content, String.class);

        AsyncJobProcessResult result = AsyncJobProcessResult.builder()
                .responseEntity(response)
                .id(id)
                .build();
        return new AsyncResult<>(result);
    }
}
