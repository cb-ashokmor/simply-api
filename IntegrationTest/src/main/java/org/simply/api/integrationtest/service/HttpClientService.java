package org.simply.api.integrationtest.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
@Getter
public class HttpClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${test.url}")
    private String url;

    @Value("${test.username}")
    private String username;

    @Value("${test.password}")
    private String password;

    private String auth;

    @PostConstruct
    public void setAuth() {
        String plainCredential = username + ":" + password;
        auth = Base64.getEncoder().encodeToString(plainCredential.getBytes());
    }

    public <T> ResponseEntity<T> get(String endpoint, Class<T> clazz) {

        return restTemplate.getForEntity(url(endpoint), clazz);
    }

    public <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<T> request = new HttpEntity<>(payload, headers);

        R r = restTemplate.postForObject(url(endpoint), request, clazz);

        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> get(String endpoint, Class<T> clazz, boolean needAuth) {

        HttpHeaders headers = new HttpHeaders();

        if (needAuth) {
            headers.add("Authorization", "Basic " + auth);
        }

        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(url(endpoint), HttpMethod.GET, request, clazz);
    }

    public <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz, boolean needAuth) {

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        if (needAuth) {
            headers.add("Authorization", "Basic " + auth);
        }

        HttpEntity<T> request = new HttpEntity<>(payload, headers);

        return restTemplate.exchange(url(endpoint), HttpMethod.POST, request, clazz);
    }

    private String url(String endpoint) {

        if (!url.endsWith("/") && !endpoint.startsWith("/")) {
            return url + "/" + endpoint;
        }

        if (url.endsWith("/") && endpoint.startsWith("/")) {
            return url + endpoint.substring(1);
        }

        return url + endpoint;
    }
}
