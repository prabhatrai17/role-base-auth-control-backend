package com.virtusa.rbac.controller;

import com.virtusa.rbac.constant.UserConstant;
import com.virtusa.rbac.exception.UserNotFoundException;
import com.virtusa.rbac.service.CustomUserDetailsService;
import com.virtusa.rbac.service.UserService;
import com.virtusa.rbac.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Validated
public class UserController {
    @Autowired
    private final UserService userService;


    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> addUser(@Valid @RequestBody UserRequestDto user){
        long userId = this.userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        UserConstant.SUCCESS_MSG,
                        String.format(UserConstant.USER_CREATED, userId),
                        HttpStatus.CREATED,
                        LocalDateTime.now()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto loginRequest){
        LoginResponseDto loginResponseDto=this.userService.validateUser(loginRequest);

        if(loginResponseDto!=null) {
//            Authentication authentication = new UsernamePasswordAuthenticationToken(ud.getUsername(), ud.getPassword(), ud.getAuthorities());
//            return (ResponseEntity) authenticationManager.authenticate(authentication);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto<>(
                            UserConstant.SUCCESS_MSG,
                            loginResponseDto,
                            HttpStatus.OK,
                            LocalDateTime.now()
                    ));
        }
        throw new UserNotFoundException("Invalid credentials");
    }

    @GetMapping
    public ResponseEntity<ResponseDto<UserResponseDto>> getUser(
            @NotNull(message = "User Id cannot be null or empty")
            @Positive(message = "User Id cannot be less than 0")
            @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "User Id must be a Long value")
            @RequestParam Long userId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(
                        UserConstant.SUCCESS_MSG,
                        this.userService.getUserByUserId(userId),
                        HttpStatus.OK,
                        LocalDateTime.now()
                ));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getAllUser(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(
                        UserConstant.SUCCESS_MSG,
                        this.userService.getAllUsers(),
                        HttpStatus.OK,
                        LocalDateTime.now()
                ));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<String>> deleteUser(
            @NotNull(message = "User Id cannot be null or empty")
            @Positive(message = "User Id cannot be less than 0")
            @RequestParam Long userId){
        if(this.userService.deleteUserByUserId(userId))
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto<>(
                            UserConstant.SUCCESS_MSG,
                            String.format(UserConstant.USER_DELETED, userId),
                            HttpStatus.OK,
                            LocalDateTime.now()
                    ));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto<>(
                        UserConstant.UNSUCCESSFUL_MSG,
                        String.format(UserConstant.INTERNAL_SERVER_ERROR),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        LocalDateTime.now()
                ));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<String>> UserUpdate(@Valid @RequestBody UpdateUserRequestDto updateUserDto){
        if(this.userService.updateUser(updateUserDto))
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto<>(
                            UserConstant.SUCCESS_MSG,
                            String.format(UserConstant.UPDATE_USER_MSG, updateUserDto.username()),
                            HttpStatus.OK,
                            LocalDateTime.now()
                    ));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto<>(
                        UserConstant.UNSUCCESSFUL_MSG,
                        String.format(UserConstant.INTERNAL_SERVER_ERROR),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        LocalDateTime.now()
                ));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<String>> logoutUser(){
        System.out.println(UserConstant.SUCCESS_MSG);
        SecurityContextHolder.clearContext();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto<>(
                        UserConstant.SUCCESS_MSG,
                        String.format(UserConstant.LOGOUT_MSG),
                        HttpStatus.OK,
                        LocalDateTime.now()
                ));
    }

    @PatchMapping("/updateRole")
    public ResponseEntity<ResponseDto<String>> updateUserRole(@Valid @RequestBody UpdateProfileDto updateUserRoleDto){
        if(this.userService.updateUserRole(updateUserRoleDto)){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto<>(
                            UserConstant.SUCCESS_MSG,
                            String.format(UserConstant.UPDATE_USER_MSG, updateUserRoleDto.userId()),
                            HttpStatus.OK,
                            LocalDateTime.now()
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto<>(
                        UserConstant.UNSUCCESSFUL_MSG,
                        String.format(UserConstant.INTERNAL_SERVER_ERROR),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        LocalDateTime.now()
                ));
    }

}
