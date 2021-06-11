package com.godeltech.exception;

public class ServiceUpdateNotMatchIdException extends RuntimeException{
    public ServiceUpdateNotMatchIdException(String message) {
        super(message);
    }
}