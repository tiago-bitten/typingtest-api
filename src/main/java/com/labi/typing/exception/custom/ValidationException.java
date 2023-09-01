package com.labi.typing.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {

    private final HttpStatus status;

    public ValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
