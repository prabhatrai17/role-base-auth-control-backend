package com.virtusa.rbac.serviceImpl;

import com.virtusa.rbac.constant.ProfileConstant;
import com.virtusa.rbac.constant.UserConstant;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.User;
import com.virtusa.rbac.exception.ProfileNotFoundException;
import com.virtusa.rbac.exception.UserAlreadyExistsException;
import com.virtusa.rbac.exception.UserNotFoundException;
import com.virtusa.rbac.mapper.UserMapper;
import com.virtusa.rbac.mapper.ValueMapper;
import com.virtusa.rbac.repository.ProfileRepository;
import com.virtusa.rbac.repository.UserRepository;
import com.virtusa.rbac.service.CustomUserDetailsService;
import com.virtusa.rbac.service.UserService;
import com.virtusa.rbac.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public boolean userExists(String username){
        return this.userRepository.findByUsername(username).isPresent();
    }
    public boolean userExists(Long userId){
        return this.userRepository.findByUserId(userId).isPresent();
    }

    @Override
    public Long createUser(UserRequestDto user) {
        if(userExists(user.username()))
            throw new UserAlreadyExistsException(
                    String.format(UserConstant.USER_ALREADY_EXISTS, user.username())
            );
        User userObj = UserMapper.toEntity(user);
        //userObj.setRoles(UserConstant.DEFAULT_USER_ROLE);
        userObj.setPassword(passwordEncoder.encode(userObj.getPassword()));
        //userObj.setProfileId(102L);
        User createdUser = this.userRepository.save(userObj);
        return createdUser.getUserId();
    }

    @Override
    public UserResponseDto getUserByUserId(Long userId) {

        User user = this.userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format(UserConstant.USER_NOT_FOUND, userId))
                );
//        long prof = user.getProfileId();
//        Profile profileOptional = this.profileRepository.findByProfileId(prof).get();
//        List<Role> roleList = profileOptional.getRoles();
//        for(Role role: roleList){
//            System.out.println(role.getRoleName());
//        }
        return UserMapper.toDto(user);
    }

    @Override
    public boolean deleteUserByUserId(Long userId) {
        if(!userExists(userId))
            throw new UserNotFoundException(String.format(UserConstant.USER_NOT_FOUND, userId));
        this.userRepository.deleteById(userId);
        return true;
    }

    @Override
    public LoginResponseDto validateUser(LoginRequestDto loginRequest) {
        User user = this.userRepository.findByEmail(loginRequest.email()).orElseThrow(
                () -> new UserNotFoundException(String.format(UserConstant.USER_NOT_FOUND, loginRequest.email()))
        );
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            UserDetails ud= customUserDetailsService.loadUserByUsername(loginRequest.email());

            return ValueMapper.convertToLoginResponseDto(ud,loginRequest.password());
        }
        return null;
    }

    @Override
    public boolean updateUser(UpdateUserRequestDto updateUser) {
        User user = this.userRepository.findByUsername(updateUser.username()).orElseThrow(
                () -> new UserNotFoundException(String.format(UserConstant.USER_NOT_FOUND, updateUser.username()))
        );
        user.setEmail(updateUser.email());
        user.setPassword(passwordEncoder.encode(updateUser.password()));
        this.userRepository.save(user);
        return true;
    }



    @Override
    public boolean updateUserRole(UpdateProfileDto updateProfileDto) {
        User user = this.userRepository.findByUserId(updateProfileDto.userId())
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format(UserConstant.USER_NOT_FOUND, updateProfileDto.userId())
                        )
                );
       Profile profile=profileRepository.findByProfileId(updateProfileDto.ProfileId())
               .orElseThrow(()->{ throw new ProfileNotFoundException(String.format(ProfileConstant.PROFILE_NOT_FOUND, updateProfileDto.ProfileId()));});

        user.setProfile(profile);
        //user.setRoles(updateUserRoleDto.userRoles());
        this.userRepository.save(user);
        return true;
    }

    public List<UserResponseDto> getAllUsers(){
        List<UserResponseDto> userResponseDtos=new ArrayList<>();
        for(User user:userRepository.findAll()){
            userResponseDtos.add(UserMapper.toDto(user));
        }
        return userResponseDtos;
    }

}
