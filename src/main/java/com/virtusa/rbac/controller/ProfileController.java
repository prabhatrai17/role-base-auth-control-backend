package com.virtusa.rbac.controller;


import com.virtusa.rbac.dto.ProfileDto;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.exception.ProfileAlreadyExistException;
import com.virtusa.rbac.exception.ProfileNotFoundException;
import com.virtusa.rbac.serviceImpl.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/addProfile")
    public ProfileDto addUSer(@RequestBody ProfileDto profileDto) throws ProfileAlreadyExistException
    {
            return profileService.addProfile(profileDto);
    }

    @GetMapping("/{profileId}")
    public Profile getProfile(@PathVariable long profileId) throws ProfileNotFoundException
    {
        return profileService.getProfile(profileId);
    }

    @GetMapping("/getAll")
    public List<Profile> getAllProfiles()
    {
        return profileService.getAllProfiles();
    }

    @DeleteMapping("/{profileId}")
    public boolean deleteProfile(@PathVariable long profileId) throws  ProfileNotFoundException
    {
         return profileService.deleteProfile(profileId);
    }

    @PutMapping("/{profileId}")
    public boolean updateProfile(@RequestBody ProfileDto profileDto,@PathVariable long profileId) throws ProfileNotFoundException
    {
        return profileService.updateProfile(profileDto,profileId);
    }

}