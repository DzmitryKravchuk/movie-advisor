package com.godeltech.exception;

public class ResourceNotFoundException extends AbstractServiceException {
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}