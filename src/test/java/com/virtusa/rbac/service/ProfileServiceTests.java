package com.virtusa.rbac.service;
import com.virtusa.rbac.dto.ProfileDto;
import com.virtusa.rbac.dto.RoleResponseDTO;
import com.virtusa.rbac.exception.ProfileAlreadyExistException;
import com.virtusa.rbac.exception.ProfileNotFoundException;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.Role;
import com.virtusa.rbac.mapper.ValueMapper;
import com.virtusa.rbac.repository.ProfileRepository;
import com.virtusa.rbac.repository.RoleRepository;

import com.virtusa.rbac.service.RoleService;

import com.virtusa.rbac.serviceImpl.ProfileService;
import jakarta.inject.Inject;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import javax.swing.text.html.Option;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProfileDto profileDto;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private RoleService roleService;

    @Mock
    private ValueMapper valueMapper;
    @InjectMocks
    private ProfileService profileService;

    @Test
    public void addProfile_NewProfile_ReturnsProfileDto() throws ProfileAlreadyExistException {
        Set<Integer> roleIds=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto1=new ProfileDto("Associate Java","Delivery Engineering -Java",roleIds);
        List<RoleResponseDTO> roles=new ArrayList<>(Arrays.asList(new RoleResponseDTO(1,"read","read access"),new RoleResponseDTO(2,"write","write access")));
        List<Role> proles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Profile profile = new Profile(1L,"Associate Java","Delivery Engineering -Java", proles);

        when(modelMapper.map(profileDto1,Profile.class)).thenReturn(profile);
        when(roleService.getRolesListByIds(roleIds)).thenReturn(roles);
        when(profileRepository.findByProfileName(profileDto1.getProfileName())).thenReturn(Optional.empty());
        when(profileRepository.save(profile)).thenReturn(profile);
//        when(valueMapper.convertToProfileDto(profile)).thenReturn(profileDto1);


        ProfileDto resultDto=profileService.addProfile(profileDto1);
        assertEquals(profileDto1, resultDto);

    }

    @Test
    public void addProfile_existingProfile_throwsProfileAlreadyExistException() throws ProfileAlreadyExistException {

        Set<Integer> roleIds=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto1=new ProfileDto("Associate Java","Delivery Engineering -Java",roleIds);
        List<RoleResponseDTO> roles=new ArrayList<>(Arrays.asList(new RoleResponseDTO(1,"read","read access"),new RoleResponseDTO(2,"write","write access")));
        List<Role> proles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));

        Profile profile = new Profile(1L,"Associate Java","Delivery Engineering -Java", proles);

        when(modelMapper.map(profileDto1,Profile.class)).thenReturn(profile);
        when(roleService.getRolesListByIds(roleIds)).thenReturn(roles);
        when(profileRepository.findByProfileName(profileDto1.getProfileName())).thenReturn(Optional.of(profile));
//        when(profileRepository.save(profile)).thenReturn(profile);
//        when(valueMapper.convertToProfileDto(profile)).thenReturn(profileDto1);

        assertThrows(ProfileAlreadyExistException.class,()->profileService.addProfile(profileDto1));
    }

    @Test
    public void updateProfile_existingProfile_ReturnTrue() throws ProfileNotFoundException
    {
        Set<Integer> roleIds=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto1=new ProfileDto("Associate Java","Delivery Engineering -Java",roleIds);
        List<RoleResponseDTO> roles=new ArrayList<>(Arrays.asList(new RoleResponseDTO(1,"read","read access"),new RoleResponseDTO(2,"write","write access")));
        List<Role> proles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Profile profile = new Profile(1L,"Associate Java","Delivery Engineering -Java", proles);

        Long profileId= 1L;
        Set<Integer> roleIds1=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto2=new ProfileDto("Associate Java1","Delivery Engineering -Java1",roleIds1);
        Optional<Profile> tempProfile=Optional.of(profile);

        when(modelMapper.map(profileDto2,Profile.class)).thenReturn(profile);
        when(roleService.getRolesListByIds(roleIds)).thenReturn(roles);
        when(profileRepository.findById(profileId)).thenReturn(tempProfile);
        when(roleService.getRolesListByIds(roleIds)).thenReturn(roles);
        when(profileRepository.save(profile)).thenReturn(profile);

        boolean result = profileService.updateProfile(profileDto2, profileId);
        assertTrue(result);

    }
    @Test
    public void updateProfile_newProfile_throwsProfileNotFoundException() throws ProfileNotFoundException
    {
        Long profileId=2L;
        Set<Integer> roleIds=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto1=new ProfileDto("Associate Java","Delivery Engineering -Java",roleIds);
        List<Role> roles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Profile profile = new Profile(1L,"Associate Java","Delivery Engineering -Java",roles);

        when(modelMapper.map(profileDto1,Profile.class)).thenReturn(profile);
//        when(roleService.getRolesListByIds(roleIds)).thenReturn(roles);
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class,()->profileService.updateProfile(profileDto1, profileId));


    }
    @Test
    public void deleteProfile_ReturnTrue() throws ProfileNotFoundException {
        List<Role> roles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Optional<Profile> profile = Optional.of(new Profile(1L, "Associate Java", "Delivery Engineering -Java", roles));
        Long profileId=1L;
        when(profileRepository.findById(profileId)).thenReturn(profile);
        boolean result =profileService.deleteProfile(profileId);
        assertTrue(result);
    }
    @Test
    public void deleteProfile_thowsProfilNotFoundException() throws ProfileNotFoundException {

        Long profileId=1L;
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class,()->profileService.deleteProfile(profileId));
    }
    @Test
    public void getProfile_ReturnsProfile() throws ProfileNotFoundException, ProfileAlreadyExistException {
        List<Role> roles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Profile profile = new Profile(1L,"Associate Java","Delivery Engineering -Java",roles);

        Long profileId=1L;
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        Profile restultProfile=profileService.getProfile(profileId);
        assertEquals(profile,restultProfile);
    }

    @Test
    public void getProfile_thowsProfileNotFoundExcetpion() throws  ProfileNotFoundException
    {
        Long profileId=1L;
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class,()->profileService.getProfile(profileId));
    }
    @Test
    public void getAllProfiles_retunrsListofProfile()
    {
        List<Role> roles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Profile profile1 = new Profile(1L,"Associate Java1","Delivery Engineering -Java1",roles);
        Profile profile2 = new Profile(2L,"Associate Java2","Delivery Engineering -Java2",roles);

        List<Profile> profiles=new ArrayList<>(Arrays.asList(profile1,profile2));
        when(profileRepository.findAll()).thenReturn(profiles);

        List<Profile> resultProfiles=profileService.getAllProfiles();
        assertEquals(profiles,resultProfiles);
    }
}