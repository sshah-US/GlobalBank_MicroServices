package com.restmicro.cards.exception;

public class InvalidCardLimitException extends RuntimeException {

    public InvalidCardLimitException() {
        super("Invalid card limit provided.");
    }

    public InvalidCardLimitException(String message) {
        super(message);
    }

    public InvalidCardLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}