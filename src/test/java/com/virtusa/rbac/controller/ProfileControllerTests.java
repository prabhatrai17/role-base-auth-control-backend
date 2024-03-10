package com.virtusa.rbac.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtusa.rbac.controller.ProfileController;
import com.virtusa.rbac.dto.ProfileDto;
import com.virtusa.rbac.exception.ProfileAlreadyExistException;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.Role;

import com.virtusa.rbac.serviceImpl.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Mock
    ProfileService profileService;


    @InjectMocks
    ProfileController profileController;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void addProfile_ValidProfile_ReturnsProfileDto() throws Exception {
        Set<Integer> roleIds=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto=new ProfileDto("Associate Java","Delivery Engineering -Java", roleIds);
        when(profileService.addProfile(any(ProfileDto.class))).thenReturn(profileDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/profile/addProfile")
                        .content(asJsonString(profileDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.profileId").value(1))
                .andExpect(jsonPath("$.profileName").value("Associate Java"))
                .andExpect(jsonPath("$.profileDescription").value("Delivery Engineering -Java"));
//                .andExpect(jsonPath("$.roleIds").value(roleIds));

    }
    @Test
    public void deleteProfile_ExistingProfileId_ReturnsTrue() throws Exception {
        // Arrange
        long profileId = 1;
        when(profileService.deleteProfile(profileId)).thenReturn(true);
        // Act
        mockMvc.perform(MockMvcRequestBuilders.delete("/profile/{profileId}", profileId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void getProfile_ExistingProfileId_ReturnsProfileDto() throws Exception {
        long profileId=1;
        List<Role> roles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        Profile profile = new Profile(1L,"Associate Java","Delivery Engineering -Java",roles);
        when(profileService.getProfile(profileId)).thenReturn(profile);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/{profileId}",profileId)
                        .content(asJsonString(profile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.profileName").value("Associate Java"))
                .andExpect(jsonPath("$.profileDescription").value("Delivery Engineering -Java"));
//                .andExpect(jsonPath("$.roleIds").value(roles));
    }

    @Test
    public void updateProfile_ValidProfile_ReturnsTrue() throws Exception {
        long profileId=1;
        Set<Integer> roleIds=new HashSet<>(Arrays.asList(1,2));
        ProfileDto profileDto=new ProfileDto("Associate Java","Delivery Engineering -Java", (Set<Integer>) roleIds);
        when(profileService.updateProfile(any(ProfileDto.class),eq(profileId))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/profile/{profileId}",profileId)
                        .content(asJsonString(profileDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));


    }

    @Test
    public void getAllProfiles_ReturnsListOfProfiles() throws Exception {

        List<Role> roles=new ArrayList<>(Arrays.asList(new Role(1,"read","read access"),new Role(2,"write","write access")));
        List<Profile> profiles = Arrays.asList(
                new Profile(1L, "Associate Java1", "Delivery Engineering -Java1", roles),
                new Profile(2L, "Associate Java2", "Delivery Engineering -Java2", roles)
        );

        when(profileService.getAllProfiles()).thenReturn(profiles);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].profileName").value("Associate Java1"))
                .andExpect(jsonPath("$[0].profileDescription").value("Delivery Engineering -Java1"))
//                .andExpect(jsonPath("$[0].roles").value(roles))
                .andExpect(jsonPath("$[1].profileName").value("Associate Java2"))
                .andExpect(jsonPath("$[1].profileDescription").value("Delivery Engineering -Java2"));
//                .andExpect(jsonPath("$[1].roles").value(roles));

    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

