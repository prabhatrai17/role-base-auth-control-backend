package com.virtusa.rbac.mapper;

import com.virtusa.rbac.dto.UserRequestDto;
import com.virtusa.rbac.dto.UserResponseDto;
import com.virtusa.rbac.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDto userDto){
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        //user.setRoles(userDto.roles());
        return user;
    }

    public static UserResponseDto toDto(User user){
        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword(),user.getProfile());
    }

}
