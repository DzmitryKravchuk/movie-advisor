package com.godeltech.exception;

public class AbstractServiceException extends RuntimeException {
    public AbstractServiceException(final String message) {
        super(message);
    }
}
