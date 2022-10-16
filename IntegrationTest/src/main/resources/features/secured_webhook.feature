Feature: verify features of secured webhook api

  Scenario: update secured webhook configuration
    When set the configuration as per webhook_config.json payload to secured webhook
    Then verify status code of 200 for secured webhook
    And get configuration as per webhook_config.json json for secured webhook