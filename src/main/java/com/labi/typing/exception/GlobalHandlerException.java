package com.labi.typing.exception;

import com.labi.typing.exception.custom.EmailAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        Set<Message> errors = Set.of(new Message("Email already exists"));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }
}
