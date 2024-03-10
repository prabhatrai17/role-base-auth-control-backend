package com.virtusa.rbac.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public record ResponseDto<T>(String status, T data, HttpStatusCode statusCode, LocalDateTime responseTime) {
}
