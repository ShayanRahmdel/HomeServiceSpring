package com.shayanr.HomeServiceSpring.exception;

public class DuplicateException extends RuntimeException{

    public DuplicateException(String message) {
        super(message);
    }
}
