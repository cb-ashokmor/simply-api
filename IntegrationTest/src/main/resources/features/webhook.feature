Feature: verify features of non secured webhook api

  Scenario: update webhook configuration
    When set the configuration as per webhook_config.json payload to webhook
    Then verify status code of 200 for webhook
    And get configuration as per webhook_config.json json for webhook