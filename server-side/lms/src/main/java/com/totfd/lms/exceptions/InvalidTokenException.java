package com.totfd.lms.exceptions;

public class InvalidTokenException extends  RuntimeException{
    public InvalidTokenException(String message){
        super(message);
    }
}
