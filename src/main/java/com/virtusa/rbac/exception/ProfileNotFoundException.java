package com.virtusa.rbac.exception;

public class ProfileNotFoundException extends RuntimeException{
    public ProfileNotFoundException(String msg){
        super(msg);
    }
}
