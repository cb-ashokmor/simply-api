package org.simply.api.integrationtest.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.simply.api.client.HttpService;
import org.simply.api.integrationtest.config.WebhookConfig;
import org.simply.api.integrationtest.service.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public abstract class BaseSteps {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired
    private WebhookConfig config;

    private HttpService httpService;

    protected ExecutionContext context() {
        return ExecutionContext.CONTEXT;
    }

    @PostConstruct
    public void init() {
        httpService = new HttpService(config.getHost(), config.getUsername(), config.getPassword());
    }

    protected String getFileContent(String filename) {

        Resource resource = resourceLoader.getResource("classpath:payloads/" + filename);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected <T> ResponseEntity<T> get(String endpoint, Class<T> clazz) {
        return httpService.get(endpoint, clazz);
    }

    protected <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz) {
        return httpService.post(endpoint, payload, clazz);
    }

    protected <T> ResponseEntity<T> get(String endpoint, Class<T> clazz, boolean needAuth) {
        return httpService.get(endpoint, clazz, needAuth);
    }

    protected <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz, boolean needAuth) {
        return httpService.post(endpoint, payload, clazz, needAuth);
    }
}
