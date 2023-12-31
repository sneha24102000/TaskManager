package com.nikozka.app.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String errorMessage) { super(errorMessage);
    }
}
