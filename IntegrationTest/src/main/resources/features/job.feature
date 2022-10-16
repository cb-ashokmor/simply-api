Feature: verify features of job

  Background:
    When reset the webhook stats
    When set the configuration as per webhook_config.json payload, controller delay 100 and processor delay 0 to webhook

  Scenario: Execute sync job
    When submit the job as per job.json payload, async false and 50 requests
    Then verify job made 50 webhook calls and min 5 and max 6 seconds to process

  Scenario: Execute async job
    When submit the job as per job.json payload, async true and 50 requests
    Then verify job made 50 webhook calls and min 0.5 and max 0.6 seconds to process