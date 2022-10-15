Feature: verify features of job

  Background:
    When the user reset the webhook stats
    When the user set the configuration as per webhook_config_for_job.json payload to webhook

  Scenario: Execute sync job
    When the user submit the job as per sync_job.json payload
    Then the user verify job made 50 webhook calls

  Scenario: Execute async job
    When the user submit the job as per async_job.json payload
    Then the user verify job made 50 webhook calls