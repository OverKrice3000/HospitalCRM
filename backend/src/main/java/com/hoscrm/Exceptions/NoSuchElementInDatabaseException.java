package com.hoscrm.Exceptions;

public class NoSuchElementInDatabaseException extends RuntimeException{
    public NoSuchElementInDatabaseException(String message) {
        super(message);
    }
}
