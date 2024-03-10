package com.virtusa.rbac.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto<T>(String apiPath, HttpStatus errorCode, T errorMsg, LocalDateTime errorTime) {

}
