package com.shayanr.HomeServiceSpring.exception;

//@ResponseStatus(value = HttpStatus.CONFLICT, reason = "this content not found")
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
