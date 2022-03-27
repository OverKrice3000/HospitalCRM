package com.hoscrm.Exceptions;

public class NotNullParameterAbsentException extends RuntimeException{
    public NotNullParameterAbsentException(String message) {
        super(message);
    }
}
