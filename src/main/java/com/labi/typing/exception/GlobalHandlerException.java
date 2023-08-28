package com.labi.typing.exception;

import com.labi.typing.exception.custom.EmailAlreadyExistsException;
import com.labi.typing.exception.custom.UsernameAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Set;

import static com.labi.typing.util.LoggerUtil.log;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        log(ex.getClass().getName() + " was thrown");
        Set<Message> errors = Set.of(new Message("Several errors occurred"));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log(ex.getClass().getName() + " was thrown");
        Set<Message> errors = Set.of(new Message(ex.getBindingResult().getFieldError().getDefaultMessage()));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        log(ex.getClass().getName() + " was thrown");
        Set<Message> errors = Set.of(new Message("Email already exists"));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        log(ex.getClass().getName() + " was thrown");
        Set<Message> errors = Set.of(new Message("Username already exists"));
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }
}
