package com.example.taxibillingsystem.exception;

public class CancellationFailedException extends RuntimeException{
    public CancellationFailedException(String message){
        super(message);
    }
}
