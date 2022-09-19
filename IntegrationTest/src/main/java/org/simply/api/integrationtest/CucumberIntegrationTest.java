package org.simply.api.integrationtest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/features",
        plugin = {"pretty", "html:./cucumber_report.html"},
        glue = {"org.simply.api.integrationtest.steps", "org.simply.api.integrationtest.config"})
public class CucumberIntegrationTest {
}
