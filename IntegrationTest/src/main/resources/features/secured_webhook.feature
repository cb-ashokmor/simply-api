Feature: verify features of secured webhook api

  Scenario: update secured webhook configuration
    When the user post configuration as per webhook_config.json payload to secured webhook
    Then the user verify status code of 200 for secured webhook
    And the user get configuration as per webhook_config.json json for secured webhook