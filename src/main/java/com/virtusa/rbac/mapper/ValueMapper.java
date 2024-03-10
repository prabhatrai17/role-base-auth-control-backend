package com.virtusa.rbac.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtusa.rbac.dto.LoginResponseDto;
import com.virtusa.rbac.dto.ProfileDto;
import com.virtusa.rbac.dto.RoleRequestDTO;
import com.virtusa.rbac.dto.RoleResponseDTO;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;

public class ValueMapper {
    public static Role convertToEntity(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setRoleName(roleRequestDTO.getRoleName());
        role.setRoleDescription(roleRequestDTO.getRoleDescription());
        return role;
    }

    public static Role convertToEntity(RoleResponseDTO roleResponseDTO) {
        Role role = new Role();
        role.setRoleId(roleResponseDTO.getRoleId());
        role.setRoleName(roleResponseDTO.getRoleName());
        role.setRoleDescription(roleResponseDTO.getRoleDescription());
        return role;
    }

    public static RoleResponseDTO convertToDTO(Role role){
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setRoleId(role.getRoleId());
        roleResponseDTO.setRoleName(role.getRoleName());
        roleResponseDTO.setRoleDescription(role.getRoleDescription());
        return roleResponseDTO;
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProfileDto convertToProfileDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileName(profile.getProfileName());
        profileDto.setProfileDescription(profile.getProfileDescription());
        profileDto.setRoleIds(profile.getRoles().stream().map((r)->r.getRoleId()).collect(Collectors.toSet()));
        return profileDto;
    }


    public static LoginResponseDto convertToLoginResponseDto(UserDetails userDetails,String password){
        LoginResponseDto loginResponseDto=new LoginResponseDto();
        loginResponseDto.setUsername(userDetails.getUsername());
        loginResponseDto.setPassword(password);
        loginResponseDto.setAuthorities(userDetails.getAuthorities()
                .stream().map((authority)->authority.getAuthority()).toList());
        return loginResponseDto;
    }
}