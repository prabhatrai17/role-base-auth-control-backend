package com.virtusa.rbac.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDto(
        @NotEmpty(message = "Email cannot be null or empty")
        @Pattern(regexp = "^[a-zA-Z0-9]+@[a-z]+.[a-z]{2,}$", message = "Invalid email address")
        String email,
        @NotEmpty(message = "Password cannot be null or empty")
        @Pattern(regexp = "^[a-zA-Z0-9@.+#$&?]{4,}$", message = "Password must be of length >= 4")
        String password) {
        @Override
        public String password() {
                return password;
        }
}
