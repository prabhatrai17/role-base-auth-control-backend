package com.virtusa.rbac.service;

import com.virtusa.rbac.constant.UserConstant;
import com.virtusa.rbac.dto.RoleResponseDTO;
import com.virtusa.rbac.entity.CustomUserDetails;
import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.Role;
import com.virtusa.rbac.entity.User;
import com.virtusa.rbac.exception.ProfileNotFoundException;
import com.virtusa.rbac.exception.UserNotFoundException;
import com.virtusa.rbac.mapper.ValueMapper;
import com.virtusa.rbac.repository.ProfileRepository;
import com.virtusa.rbac.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(username);
        if(user.isEmpty())
            throw new UserNotFoundException(String.format(UserConstant.USER_NOT_FOUND, username));
        List<RoleResponseDTO> roleList=new ArrayList<>();
        if(user.get().getProfile()!=null){
        Profile profile = profileRepository.findByProfileId(user.get().getProfile().getProfileId())
                .orElseThrow(()->new ProfileNotFoundException("profile not allocated to this user"));



            Optional<Set<Integer>> roleIds=profileRepository.findRolesByProfileId(profile.getProfileId());
            roleList=roleService.getRolesListByIds(roleIds.get());
        }
        System.out.println(roleList);
        Set<String> roleNames = new HashSet<>();
        for(RoleResponseDTO role: roleList){
            roleNames.add(role.getRoleName());
        }
        System.out.println(roleNames);
        return user.map(u -> new CustomUserDetails(u, roleNames)).orElseThrow(
                () -> new UserNotFoundException(String.format("User not found with email: %s", username))
        );
    }
}
