package com.totfd.lms.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
