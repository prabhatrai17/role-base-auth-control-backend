package com.virtusa.rbac.exception;

public class RoleAlreadyExistsException extends RuntimeException{
    public RoleAlreadyExistsException(String msg){
        super(msg);
    }
}
