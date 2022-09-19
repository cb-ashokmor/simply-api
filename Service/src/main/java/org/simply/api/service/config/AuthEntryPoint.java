package org.simply.api.service.config;

import org.simply.api.common.model.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@AllArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        Error error = Error.builder().error("Authentication failed").build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, error);
        out.flush();
    }
}
