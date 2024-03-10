package com.virtusa.rbac.serviceImpl;


import com.virtusa.rbac.dto.ProfileDto;
import com.virtusa.rbac.dto.RoleResponseDTO;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.Role;
import com.virtusa.rbac.exception.ProfileAlreadyExistException;
import com.virtusa.rbac.exception.ProfileNotFoundException;
import com.virtusa.rbac.mapper.ValueMapper;
import com.virtusa.rbac.repository.ProfileRepository;
import com.virtusa.rbac.repository.RoleRepository;
import com.virtusa.rbac.service.RoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    ProfileDto profiledto;
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    /*public ProfileDto addProfile(ProfileDto profileDto)throws ProfileAlreadyExistException
    {

        Profile profile =modelMapper.map(profileDto,Profile.class);
        List<RoleResponseDTO> responseDTOList = roleService.getRolesListByIds(profileDto.getRoleIds());
        List<Role> rolesList = new ArrayList<>();
        for(RoleResponseDTO responseDto: responseDTOList){
            rolesList.add(ValueMapper.convertToEntity(responseDto));
        }
        profile.setRoles(rolesList);
//        System.out.println("profile after setting roles"+profile);
        boolean isProfileExist =profileRepository.findByProfileName(profileDto.getProfileName()).isPresent();
//        System.out.println("is Profile exists =*************   "+isProfileExist);
        if (isProfileExist==true)
        {
            throw new ProfileAlreadyExistException("Profile Already Exists");

        }

        if(profileRepository.save(profile)!=null)
            profiledto =  modelMapper.map(profile,ProfileDto.class);

       // throw new ProfileAlreadyExistException("Profile Already Exists");
        return profiledto;
    }*/

    public ProfileDto addProfile(ProfileDto profileDto)throws ProfileAlreadyExistException
    {

        Profile profile =modelMapper.map(profileDto,Profile.class);
        List<RoleResponseDTO> roleDtos=roleService.getRolesListByIds(profileDto.getRoleIds());
        List<Role> roles=new ArrayList<>();
        for(RoleResponseDTO responseDTO:roleDtos){
            roles.add(ValueMapper.convertToEntity(responseDTO));
            //System.out.println(roles);
            logger.info("ProfileSerivce getRolesListByIds() in addProfile() : "+roles);

        }
        profile.setRoles(roles);
        boolean isProfileExist =profileRepository.findByProfileName(profileDto.getProfileName()).isPresent();
        if (isProfileExist==true)
        {
            throw new ProfileAlreadyExistException("Profile Already Exists");

        }

        Profile profile1=profileRepository.save(profile);
        if(profile1!=null)
            profiledto = ValueMapper.convertToProfileDto(profile1);
        return profiledto;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean deleteProfile(Long profileId)throws ProfileNotFoundException
    {

        if (profileRepository.findById(profileId).isEmpty())
        {
            throw new ProfileNotFoundException("Profile with id : "+profileId+" Not Found");
        }
        else
        {
            Optional<Profile> deletedProfile = profileRepository.findById(profileId);
            if(deletedProfile.isPresent())
                profileRepository.deleteById(profileId);
            logger.info("ProfileSerivce deleteProfile() : "+true);

            return deletedProfile.isPresent();

        }

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean updateProfile(ProfileDto profileDto,Long profileId) throws ProfileNotFoundException {
        Profile profile=modelMapper.map(profileDto,Profile.class);
        Optional<Profile> tempProfile=profileRepository.findById(profileId);
        Profile profile1=null;
        if(tempProfile.isPresent())
        {
            profile1=tempProfile.get();
            profile1.setProfileName(profile.getProfileName());
            profile1.setProfileDescription(profile.getProfileDescription());
            List<RoleResponseDTO> responseDTOList = roleService.getRolesListByIds(profileDto.getRoleIds());
            List<Role> rolesList = new ArrayList<>();
            for(RoleResponseDTO responseDto: responseDTOList){
                rolesList.add(ValueMapper.convertToEntity(responseDto));
            }
            //List<Role> roles=roleService.getRolesListByIds(profileDto.getRoleIds());
            profile1.setRoles(rolesList);
            profileRepository.save(profile1);
            logger.info("ProfileSerivce updateProfile() : "+true);

            return true;
        }
        throw new ProfileNotFoundException("profile not found with id : "+profileId);
    }

    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Profile getProfile(Long profileId) throws ProfileNotFoundException
    {
        Optional<Profile> tempProfile=profileRepository.findById(profileId);
        //Profile profile = tempProfile.get();
        ProfileDto profileDto;
        if(tempProfile.isPresent()) {
//            profileDto = modelMapper.map(tempProfile.get(), ProfileDto.class);
            logger.info("ProfileSerivce getProfile() : "+tempProfile.get());
            return  tempProfile.get();
        }

        throw  new ProfileNotFoundException("profile not found with id : "+profileId);

    }

    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Profile> getAllProfiles()
    {
        logger.info("Profile Service getAllProfiles  :"+profileRepository.findAll());
        return profileRepository.findAll();
    }
}



//package com.virtusa.rbac.serviceImpl;
//
//
//import com.virtusa.rbac.dto.ProfileDto;
//import com.virtusa.rbac.dto.RoleResponseDTO;
//import com.virtusa.rbac.entity.Profile;
//import com.virtusa.rbac.entity.Role;
//import com.virtusa.rbac.exception.ProfileAlreadyExistException;
//import com.virtusa.rbac.exception.ProfileNotFoundException;
//import com.virtusa.rbac.mapper.ValueMapper;
//import com.virtusa.rbac.repository.ProfileRepository;
//import com.virtusa.rbac.repository.RoleRepository;
//import com.virtusa.rbac.service.RoleService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Component
//public class ProfileService {
//    @Autowired
//    private ProfileRepository profileRepository;
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    RoleService roleService;
//
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    ProfileDto profiledto;
//    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    /*public ProfileDto addProfile(ProfileDto profileDto)throws ProfileAlreadyExistException
//    {
//
//        Profile profile =modelMapper.map(profileDto,Profile.class);
//        List<RoleResponseDTO> responseDTOList = roleService.getRolesListByIds(profileDto.getRoleIds());
//        List<Role> rolesList = new ArrayList<>();
//        for(RoleResponseDTO responseDto: responseDTOList){
//            rolesList.add(ValueMapper.convertToEntity(responseDto));
//        }
//        profile.setRoles(rolesList);
////        System.out.println("profile after setting roles"+profile);
//        boolean isProfileExist =profileRepository.findByProfileName(profileDto.getProfileName()).isPresent();
////        System.out.println("is Profile exists =*************   "+isProfileExist);
//        if (isProfileExist==true)
//        {
//            throw new ProfileAlreadyExistException("Profile Already Exists");
//
//        }
//
//        if(profileRepository.save(profile)!=null)
//            profiledto =  modelMapper.map(profile,ProfileDto.class);
//
//       // throw new ProfileAlreadyExistException("Profile Already Exists");
//        return profiledto;
//    }*/
//
//    public ProfileDto addProfile(ProfileDto profileDto)throws ProfileAlreadyExistException
//    {
//
//        Profile profile =modelMapper.map(profileDto,Profile.class);
//        List<RoleResponseDTO> roleDtos=roleService.getRolesListByIds(profileDto.getRoleIds());
//        List<Role> roles=new ArrayList<>();
//        for(RoleResponseDTO responseDTO:roleDtos){
//            roles.add(ValueMapper.convertToEntity(responseDTO));
//        }
//        profile.setRoles(roles);
//        System.out.println(roles+"###################3");
//        //boolean isProfileExistForThatRoles=isProfileExistsForRoles(roles);
//        //System.out.println(isProfileExistForThatRoles);
//        boolean isProfileExist =profileRepository.findByProfileName(profileDto.getProfileName()).isPresent();
////
//        if (isProfileExist==true )
//        {
//            throw new ProfileAlreadyExistException("Profile Already Exists");
//
//        }
//       // if(isProfileExistForThatRoles==true){throw new ProfileAlreadyExistException("Profile Already Exists for that roles");}
//
//        Profile profile1=profileRepository.save(profile);
//        if(profile1!=null)
//            profiledto = ValueMapper.convertToProfileDto(profile1);
//        return profiledto;
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public boolean deleteProfile(Long profileId)throws ProfileNotFoundException
//    {
//
//        if (profileRepository.findById(profileId).isEmpty())
//        {
//            throw new ProfileNotFoundException("Profile with id : "+profileId+" Not Found");
//        }
//        else
//        {
//            Optional<Profile> deletedProfile = profileRepository.findById(profileId);
//            if(deletedProfile.isPresent())
//                profileRepository.deleteById(profileId);
//            return deletedProfile.isPresent();
//
//        }
//
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public boolean updateProfile(ProfileDto profileDto,Long profileId) throws ProfileNotFoundException {
//        Profile profile=modelMapper.map(profileDto,Profile.class);
//        Optional<Profile> tempProfile=profileRepository.findById(profileId);
//        Profile profile1=null;
//        if(tempProfile.isPresent())
//        {
//            profile1=tempProfile.get();
//            profile1.setProfileName(profile.getProfileName());
//            profile1.setProfileDescription(profile.getProfileDescription());
//            List<RoleResponseDTO> responseDTOList = roleService.getRolesListByIds(profileDto.getRoleIds());
//            List<Role> rolesList = new ArrayList<>();
//            for(RoleResponseDTO responseDto: responseDTOList){
//                rolesList.add(ValueMapper.convertToEntity(responseDto));
//            }
//            //List<Role> roles=roleService.getRolesListByIds(profileDto.getRoleIds());
//            profile1.setRoles(rolesList);
//            profileRepository.save(profile1);
//            return true;
//        }
//        throw new ProfileNotFoundException("profile not found with id : "+profileId);
//    }
//
//   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public Profile getProfile(Long profileId) throws ProfileNotFoundException
//    {
//        Optional<Profile> tempProfile=profileRepository.findById(profileId);
//        //Profile profile = tempProfile.get();
//        ProfileDto profileDto;
//        if(tempProfile.isPresent()) {
////            profileDto = modelMapper.map(tempProfile.get(), ProfileDto.class);
//            return  tempProfile.get();
//        }
//
//        throw  new ProfileNotFoundException("profile not found with id : "+profileId);
//
//    }
//
//   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public List<Profile> getAllProfiles()
//    {
//        return profileRepository.findAll();
//    }
//
//    public boolean isProfileExistsForRoles(List<Role> roleList){
//        List<Profile> profileList=getAllProfiles();
//        for(Profile profile:profileList){
//            if(roleList.containsAll(profile.getRoles()) && roleList.size()==profile.getRoles().size()){
//                return true;
//            }
//        }
//        return false;
//    }
//}
