Feature: verify features of non secured webhook api

  Scenario: update webhook configuration
    When the user post configuration as per webhook_config.json payload to webhook
    Then the user verify status code of 200 for webhook
    And the user get configuration as per webhook_config.json json for webhook