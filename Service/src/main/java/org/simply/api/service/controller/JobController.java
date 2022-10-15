package org.simply.api.service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.common.model.Error;
import org.simply.api.common.model.job.JobPayload;
import org.simply.api.common.model.job.JobResponse;
import org.simply.api.service.exception.IllegalArgumentException;
import org.simply.api.service.service.JobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static java.util.Objects.isNull;

@RestController
@AllArgsConstructor
@RequestMapping("jobs")
@Slf4j
public class JobController {
    private final JobService jobService;

    @PostMapping
    public JobResponse post(@RequestBody JobPayload payload) {
        if (isNull(payload)
                || isNull(payload.getWebhookPayload())
                || isNull(payload.getWebhookPayload().getContent())) {
            Error error = Error.builder().error("WebhookPayload should not be empty to execute the job").build();
            throw new IllegalArgumentException(error);
        }

        if (payload.getRequests() <= 0) {
            Error error = Error.builder().error("Requests should not be zero or negative value to execute the job").build();
            throw new IllegalArgumentException(error);
        }

        Date begin = new Date();

        int successful = payload.isAsync() ? jobService.parallelProcess(payload) : jobService.process(payload);

        Date end = new Date();

        return JobResponse.builder()
                .message("Job execution completed")
                .begin(begin)
                .end(end)
                .totalRequest(payload.getRequests())
                .successful(successful)
                .duration((float) (end.getTime() - begin.getTime()) / 1000)
                .build();
    }
}
