package com.virtusa.rbac.dto;

import com.virtusa.rbac.entity.Profile;

public record UserResponseDto(long userId, String username, String email, String password, Profile profile) {
}
