package org.simply.api.integrationtest.config;


import io.cucumber.spring.CucumberContextConfiguration;
import org.simply.api.integrationtest.Application;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
public class CucumberSpringConfig {
}
