package com.shayanr.HomeServiceSpring.exception;

public class IsEmptyFieldException extends RuntimeException{
    public IsEmptyFieldException(String message){
        super(message);
    }
}
