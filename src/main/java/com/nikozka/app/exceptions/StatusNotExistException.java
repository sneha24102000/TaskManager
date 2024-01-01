package com.nikozka.app.exceptions;

public class StatusNotExistException extends RuntimeException {
    public StatusNotExistException(String errorMessage) { super(errorMessage);
    }
}
