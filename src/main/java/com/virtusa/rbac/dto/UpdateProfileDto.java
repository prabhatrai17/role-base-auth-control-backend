package com.virtusa.rbac.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateProfileDto(
        @NotNull(message = "User Id cannot be null or empty")
        @Positive(message = "User Id cannot be less than 0")
        Long userId,
        @NotNull(message = "Profile Id cannot be null or empty")
        @Positive(message = "Profile Id must be greater than 0")
        Long ProfileId
) {
}
