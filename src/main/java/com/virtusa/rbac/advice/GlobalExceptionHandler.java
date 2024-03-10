package com.virtusa.rbac.advice;

import com.virtusa.rbac.dto.ErrorResponseDto;
import com.virtusa.rbac.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest){
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        Map<String, String> errorMap = new HashMap<>();

        errorList.forEach((error)->{
            String errorField = ((FieldError)error).getField();
            String errorMsg = error.getDefaultMessage();
            errorMap.put(errorField, errorMsg);
        });

        ErrorResponseDto<Map<String, String>> errorResponseDto = new ErrorResponseDto<>(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                errorMap,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto<String> handleInternalServerErrorException(Exception ex, WebRequest webRequest){

        return new ErrorResponseDto<>(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponseDto<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest webRequest){

        return new ErrorResponseDto<>(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseDto<String> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest){
        return new ErrorResponseDto<>(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ApplicationIsAlreadyPresentException.class)
    public ResponseEntity handleApplicationIsAlreadyPresentException(ApplicationIsAlreadyPresentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity handleApplicationNotFoundException(ApplicationNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProfileAlreadyExistException.class)
    public ResponseEntity handleProfileAlreadyExistException(ProfileAlreadyExistException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity handleProfileNotFoundException(ProfileNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity handleRoleAlreadyExistsException(RoleAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity handleRoleNotFoundException(RoleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }



}
