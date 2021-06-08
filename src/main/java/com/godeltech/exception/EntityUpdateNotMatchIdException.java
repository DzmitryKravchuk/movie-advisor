package com.godeltech.exception;

public class EntityUpdateNotMatchIdException extends RuntimeException{
    public EntityUpdateNotMatchIdException(String message) {
        super(message);
    }
}