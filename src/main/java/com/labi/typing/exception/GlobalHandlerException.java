package com.labi.typing.exception;

import com.labi.typing.exception.custom.FileReadException;
import com.labi.typing.exception.custom.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Set;

import static com.labi.typing.util.LoggerUtil.log;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log(ex.getClass().getSimpleName() + " was thrown");
        Set<Message> errors = Set.of(new Message(ex.getBindingResult().getFieldError().getDefaultMessage()));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                errors
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<ApiError> handleFileReadException(FileReadException ex, HttpServletRequest request) {
        log(ex.getClass().getSimpleName() + " was thrown");
        Set<Message> errors = Set.of(new Message("File read exception"));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                errors
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex, WebRequest request) {
        log(ex.getClass().getSimpleName() + " was thrown");
        Set<Message> errors = Set.of(new Message(ex.getMessage()));
        ApiError apiError = new ApiError(
                Instant.now(),
                ex.getStatus().value(),
                request.getContextPath(),
                errors
        );
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        log(ex.getClass().getSimpleName() + " was thrown");
        Set<Message> errors = Set.of(new Message("Username or password is incorrect"));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                errors
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}