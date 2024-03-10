package com.virtusa.rbac.service;

import com.virtusa.rbac.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
//import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

    Long createUser(UserRequestDto user);
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    UserResponseDto getUserByUserId(Long userId);
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    boolean deleteUserByUserId(Long userId);
    LoginResponseDto validateUser(LoginRequestDto loginRequest);
    @PreAuthorize("hasAuthority('ROLE_USER')")
    boolean updateUser(UpdateUserRequestDto updateUser);
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    boolean updateUserRole(UpdateProfileDto updateUserRoleDto);
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResponseDto> getAllUsers();

}
