package com.nikozka.app.exceptions;

public class PasswordNotCorrectException extends RuntimeException {
    public PasswordNotCorrectException(String errorMessage) { super(errorMessage);
    }
}
