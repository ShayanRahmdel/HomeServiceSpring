package com.shayanr.HomeServiceSpring.exception;

public class NotFoundEmail extends RuntimeException{
    public NotFoundEmail(String message){
        super(message);
    }
}
