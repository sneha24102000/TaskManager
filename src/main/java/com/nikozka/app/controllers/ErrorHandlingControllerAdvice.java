package com.nikozka.app.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UserNotFoundException ex) {
//        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(UserAlreadyExistException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException ex) {
//        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.joining(", "));
//
//        return new ResponseEntity<>(new ErrorResponse(errorMessages), status);
//    }
//
//    @ExceptionHandler(DateNotParsedException.class)
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//    public ResponseEntity<ErrorResponse> handleDateNotParsedException(DateNotParsedException ex) {
//        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//
//    @ExceptionHandler(TableNotExistException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ErrorResponse> handleTableNotExistException(TableNotExistException ex) {
//        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
//    }
}
