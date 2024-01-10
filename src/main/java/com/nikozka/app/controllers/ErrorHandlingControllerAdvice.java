package com.nikozka.app.controllers;

import com.nikozka.app.exceptions.InvalidStateException;
import com.nikozka.app.exceptions.TaskNotFoundException;
import com.nikozka.app.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage())).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> messageSource.getMessage(fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale()))
                .collect(Collectors.joining(", "));

        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(status, errorMessages)).build();
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage())).build();
    }

    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<ProblemDetail> handleInvalidStateException(InvalidStateException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage())).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage())).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred", ex);
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred")).build();

    }
}
