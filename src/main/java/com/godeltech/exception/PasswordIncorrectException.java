package com.godeltech.exception;

public class PasswordIncorrectException extends AbstractServiceException {
    public PasswordIncorrectException(final String message) {
        super(message);
    }
}
