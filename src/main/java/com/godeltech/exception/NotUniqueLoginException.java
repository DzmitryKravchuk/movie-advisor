package com.godeltech.exception;

public class NotUniqueLoginException extends AbstractServiceException {
    public NotUniqueLoginException(final String message) {
        super(message);
    }
}
