package com.virtusa.rbac.controller;

import com.virtusa.rbac.dto.UserProfileDto;
import com.virtusa.rbac.dto.UserResponseDto;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.User;
import com.virtusa.rbac.entity.UserProfile;
import com.virtusa.rbac.serviceImpl.ProfileService;
import com.virtusa.rbac.serviceImpl.UserProfileService;
import com.virtusa.rbac.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userProfileApi")
public class UserProfileController
{
    @Autowired
    UserProfileService userProfileService;


    @PostMapping
    public UserProfile addUserProfile(@RequestBody UserProfileDto userProfileDto)
    {   try{
        return userProfileService.adUserProfile(userProfileDto);
    }
    catch(DataIntegrityViolationException Ex){
        throw new DataIntegrityViolationException("this user is already associated with profile");
    }

    }

    @GetMapping
    public List<UserProfile> getAllUserProfile()
    {
        return userProfileService.geAllUserProfile();
    }

    @GetMapping("/byProfile/{profileId}")
    public UserProfile getAllUserModulesByProfileId(@PathVariable int profileId)
    {
        return userProfileService.geAllUserByProfile(profileId);
    }

    @GetMapping("/byProfileName/{profileName}")
    public UserProfile getAllUserModulesByProfileName(@PathVariable String profileName)
    {
        return userProfileService.getAllUserModuleByProfileName(profileName);
    }

    @GetMapping("/{userProfileId}")
    public UserProfile getUserProfile(@PathVariable int userProfileId)
    {
        return userProfileService.geUserProfile(userProfileId);
    }

    @PutMapping("/{userProfileId}")
    public UserProfile updateUserProfile(@PathVariable int userProfileId, @RequestBody UserProfileDto userProfileDto)
    {
        return userProfileService.upUserProfile(userProfileId, userProfileDto);
    }

    @DeleteMapping("/{userProfileId}")
    public boolean deleteUserprofile(@PathVariable int userProfileId)
    {
        return userProfileService.deUserProfile(userProfileId);
    }

    //List of Users from UserDetailsController





    @PutMapping("/byProfilewiseAdd")
    public UserProfile addUserWithProfile(@RequestBody UserProfileDto userProfileDto)
    {   try{
        return userProfileService.adUserWithProfile(userProfileDto);
        }
        catch(DataIntegrityViolationException Ex){
            throw new DataIntegrityViolationException("this user is already associated with profile");
        }

    }

    @DeleteMapping("/userAssociatedWithProfile/{profileName}/{userId}")
    public UserProfile deleteUserAssociatedWithProfile(@PathVariable String profileName, @PathVariable int userId)
    {
        System.out.println("profileName"+profileName+" id:"+userId);
        return userProfileService.delUserAssociatedWithProfile(profileName,userId);
    }

}
