package com.virtusa.rbac.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto(
        @NotEmpty(message = "Username cannot be null or empty")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$", message = "Username must be 4 to 10 characters long")
        String username,
        @NotEmpty(message = "Email address cannot be null or empty")
        @Pattern(regexp = "^[a-zA-Z0-9]+@[a-z]+.[a-z]{2,}$", message = "Invalid email address")
        String email,
        @NotEmpty(message = "Password cannot be null or empty")
        @Pattern(regexp = "^[a-zA-Z0-9@.+#$&?]{4,}$", message = "Password must be of length >= 4")
        String password
) {
}
