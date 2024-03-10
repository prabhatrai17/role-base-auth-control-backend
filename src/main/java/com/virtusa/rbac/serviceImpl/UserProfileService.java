package com.virtusa.rbac.serviceImpl;

import com.virtusa.rbac.dto.UserProfileDto;

import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.User;
import com.virtusa.rbac.entity.UserProfile;
import com.virtusa.rbac.repository.ProfileRepository;
import com.virtusa.rbac.repository.UserProfileRepository;
import com.virtusa.rbac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService
{
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userDetailsRepository;

    @Autowired
    ProfileRepository profileRepository;

    public UserProfile adUserProfile(UserProfileDto userProfileDto)
    {
        List<User> usersList = new ArrayList<>();
        List<Long> userIds=userProfileDto.getUserIds();
        for(long userId:userIds)
        {
            Optional<User> user=userDetailsRepository.findById(userId);
            if(user.isPresent())
            {
                usersList.add(user.get());
            }
        }

        Optional<Profile> profile=profileRepository.findById(userProfileDto.getProfileId());

        UserProfile userProfile=new UserProfile();
        if(profile.isPresent())
            userProfile.setProfile(profile.get());

        userProfile.setUsers(usersList);

        return userProfileRepository.save(userProfile);
    }

    public List<UserProfile> geAllUserProfile()
    {
        return userProfileRepository.findAll();
    }

    public UserProfile geUserProfile(int userProfileId)
    {
        Optional<UserProfile> userProfile= userProfileRepository.findById(userProfileId);

        if(userProfile.isPresent())
            return userProfile.get();
        else
            return null;

    }

    public UserProfile geAllUserByProfile(long profileId)
    {
        Optional<Profile> profile=profileRepository.findById(profileId);
        if(profile.isPresent())
            return userProfileRepository.findByProfile(profile.get());
        else
            return null;
    }

    public UserProfile getAllUserModuleByProfileName(String profileName)
    {
        Optional<Profile> profile=profileRepository.findByProfileName(profileName);
        System.out.println(profileName);
        if(profile.isPresent()){

          UserProfile userProfile=userProfileRepository.findByProfile(profile.get());
          if(userProfile!=null){userProfile.setProfile(profile.get());}
          System.out.println(userProfile);
            return userProfile;}
        else
            return null;
    }


    public UserProfile upUserProfile(int userProfileId, UserProfileDto userProfileDto)
    {
        Optional<UserProfile> userProfile = userProfileRepository.findById(userProfileId);

        if(userProfile.isPresent())
        {
            UserProfile userProfile1= userProfile.get();

            if(userProfileDto.getProfileId() != 0)
            {
                Optional<Profile> profile=profileRepository.findById(userProfileDto.getProfileId());
                if(profile.isPresent())
                {
                    userProfile1.setProfile(profile.get());
                }
            }

            if(userProfileDto.getUserIds() != null)
            {
                List<User> usersList = new ArrayList<>();
                List<Long> userIds=userProfileDto.getUserIds();
                for(long userId:userIds)
                {
                    Optional<User> user=userDetailsRepository.findById(userId);
                    if(user.isPresent())
                    {
                        usersList.add(user.get());
                    }
                }
                userProfile1.setUsers(usersList);
            }

            return userProfileRepository.save(userProfile1);

        }

        return null;
    }

    public boolean deUserProfile(int userProfileId)
    {
        try
        {
            userProfileRepository.deleteById(userProfileId);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }


    public UserProfile adUserWithProfile(UserProfileDto userProfileDto)
    {
        //here i need to fetch the profile and pass the profile into findBYprofile object then i nedd to retruve list and in that list i need to add users
//        userProfileRepository.findByProfile(userProfileDto.)
        Optional<Profile> profile=profileRepository.findById(userProfileDto.getProfileId());
        if(profile.isPresent())
        {
//            List<UserProfile> userProfiles=userProfileRepository.findByProfile(profile.get());
//            return userProfiles;

            UserProfile userProfile= userProfileRepository.findByProfile(profile.get());
            List<User> userList=userProfile.getUsers();
            List<Long> newUserIds=userProfileDto.getUserIds();

            for(long userId: newUserIds)
            {
                Optional<User> newUserDetails=userDetailsRepository.findById(userId);
                if(newUserDetails.isPresent())
                {
                    userList.add(newUserDetails.get());
                }
            }
            userProfile.setUsers(userList);
            return userProfileRepository.save(userProfile);
        }
        return null;
    }

    public UserProfile delUserAssociatedWithProfile(String profileName, int userId)
    {
        Optional<Profile> profile=profileRepository.findByProfileName(profileName);
        if(profile.isPresent())
        {
            UserProfile userProfile=userProfileRepository.findByProfile(profile.get());
            List<User> users=userProfile.getUsers();
            Iterator<User> iterator=users.iterator();
            while(iterator.hasNext())
            {
                User userDetails=iterator.next();
                if(userDetails.getUserId()==userId)
                {
                    iterator.remove();


                }
            }
            if(userProfile.getUsers().isEmpty())
            {
                userProfileRepository.delete(userProfile);
            }
            else{
                return userProfileRepository.save(userProfile);
            }
        }
        return null;

    }

}
