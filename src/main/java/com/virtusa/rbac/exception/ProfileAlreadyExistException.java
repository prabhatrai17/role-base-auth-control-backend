package com.virtusa.rbac.exception;

public class ProfileAlreadyExistException extends  Exception{
    public ProfileAlreadyExistException(String msg)
    {
        super(msg);
    }
}
