package com.virtusa.rbac.exception;

public class ApplicationNotFoundException extends RuntimeException{

    public ApplicationNotFoundException(String message){
        super(message);
    }
}
