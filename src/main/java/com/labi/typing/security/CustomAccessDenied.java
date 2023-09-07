package com.labi.typing.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labi.typing.exception.ApiError;
import com.labi.typing.exception.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;

import static com.labi.typing.util.LoggerUtil.log;

@Component
public class CustomAccessDenied implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        log(accessDeniedException.getClass().getSimpleName() + " was thrown");
        Set<Message> errors = Set.of(new Message(accessDeniedException.getMessage()));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpServletResponse.SC_FORBIDDEN,
                request.getRequestURI(),
                errors
        );

        objectMapper.writeValue(response.getWriter(), apiError);
    }
}
