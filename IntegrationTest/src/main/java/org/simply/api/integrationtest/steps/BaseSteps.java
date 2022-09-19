package org.simply.api.integrationtest.steps;

import org.simply.api.integrationtest.service.ExecutionContext;
import org.simply.api.integrationtest.service.HttpClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public abstract class BaseSteps {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private HttpClientService httpClient;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    protected ExecutionContext context() {
        return ExecutionContext.CONTEXT;
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
        return httpClient.get(endpoint, clazz);
    }

    protected <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz) {
        return httpClient.post(endpoint, payload, clazz);
    }

    protected <T> ResponseEntity<T> get(String endpoint, Class<T> clazz, boolean needAuth) {
        return httpClient.get(endpoint, clazz, needAuth);
    }

    protected <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz, boolean needAuth) {
        return httpClient.post(endpoint, payload, clazz, needAuth);
    }
}
