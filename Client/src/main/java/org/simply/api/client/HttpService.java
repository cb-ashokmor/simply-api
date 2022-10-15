package org.simply.api.client;

import lombok.Getter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static java.util.Objects.nonNull;

@Getter
public class HttpService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String host;
    private String auth;

    public HttpService(String host, String username, String password) {
        String plainCredential = username + ":" + password;
        this.auth = Base64.getEncoder().encodeToString(plainCredential.getBytes());
        this.host = host;
    }

    public HttpService(String host) {
        this.host = host;
    }

    public <T> ResponseEntity<T> get(String endpoint, Class<T> clazz) {

        return restTemplate.getForEntity(url(endpoint), clazz);
    }

    public <T, R> ResponseEntity<R> post(String endpoint, T payload, Class<R> clazz) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<T> request = null;

        if (nonNull(payload)) {
            request = new HttpEntity<>(payload, headers);
        }

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

        if (!host.endsWith("/") && !endpoint.startsWith("/")) {
            return host + "/" + endpoint;
        }

        if (host.endsWith("/") && endpoint.startsWith("/")) {
            return host + endpoint.substring(1);
        }

        return host + endpoint;
    }
}
