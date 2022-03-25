package com.hoscrm.Exceptions;

import java.rmi.UnexpectedException;

public class UnexpectedUrlParameterException extends RuntimeException {
    public UnexpectedUrlParameterException(String message) {
        super(message);
    }
}
