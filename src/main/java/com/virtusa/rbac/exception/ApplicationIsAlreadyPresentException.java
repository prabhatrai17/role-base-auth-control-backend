package com.virtusa.rbac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApplicationIsAlreadyPresentException extends RuntimeException{
    public ApplicationIsAlreadyPresentException(String message){
        super(message);
    }
}
