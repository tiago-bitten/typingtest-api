package com.labi.typing.exception;

import com.labi.typing.exception.custom.FileReadException;
import com.labi.typing.exception.custom.ValidationException;
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex, HttpServletRequest request) {
        log(ex.getClass().getSimpleName() + " was thrown");
        Set<Message> errors = Set.of(new Message(ex.getMessage()));
        ApiError apiError = new ApiError(
                Instant.now(),
                ex.getStatus().value(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }
}
