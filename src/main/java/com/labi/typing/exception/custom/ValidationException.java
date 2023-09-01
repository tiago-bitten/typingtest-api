package com.labi.typing.exception.custom;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

    private HttpStatus status;

    public ValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
