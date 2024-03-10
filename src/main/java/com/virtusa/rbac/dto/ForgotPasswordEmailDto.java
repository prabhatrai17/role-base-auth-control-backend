package com.virtusa.rbac.dto;

public record ForgotPasswordEmailDto(String recipient, String subject, String body) {
}
