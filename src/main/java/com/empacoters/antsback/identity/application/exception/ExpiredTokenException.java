package com.empacoters.antsback.identity.application.exception;

public class ExpiredTokenException extends InvalidTokenException {
    public ExpiredTokenException(String message) {
        super(message);
    }
}
