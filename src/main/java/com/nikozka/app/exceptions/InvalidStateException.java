package com.nikozka.app.exceptions;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String errorMessage) { super(errorMessage);
    }
}
