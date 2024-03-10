package com.virtusa.rbac.serviceImpl;


import com.virtusa.rbac.dto.RoleRequestDTO;
import com.virtusa.rbac.dto.RoleResponseDTO;
import com.virtusa.rbac.entity.Role;
import com.virtusa.rbac.exception.RoleAlreadyExistsException;
import com.virtusa.rbac.exception.RoleNotFoundException;
import com.virtusa.rbac.mapper.ValueMapper;
import com.virtusa.rbac.repository.RoleRepository;
import com.virtusa.rbac.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponseDTO addRole(RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO roleResponseDTO;

        if(this.getAllRoles().stream().anyMatch(role->role.getRoleName().equals(roleRequestDTO.getRoleName())))
            throw new RoleAlreadyExistsException(roleRequestDTO.getRoleName() + " Role Already Exists");

        log.info("RoleService:addRole execution started");
        Role role = ValueMapper.convertToEntity(roleRequestDTO);
        log.debug("RoleService:addRole parameters: ", ValueMapper.jsonAsString(roleRequestDTO));

        Role roleResults = roleRepository.save(role);
        roleResponseDTO = ValueMapper.convertToDTO(roleResults);
        log.debug("RoleService:addRole received response from database: ", ValueMapper.jsonAsString(roleResponseDTO));

        log.info("RoleService:addRole execution ended.");
        return roleResponseDTO;
    }

    @Override
    public RoleResponseDTO updateRole(RoleRequestDTO roleRequestDTO, int roleId) {
        RoleResponseDTO roleResponseDTO;

        if(this.getAllRoles().stream().anyMatch(role->(role.getRoleName().equals(roleRequestDTO.getRoleName()) && role.getRoleId()!=roleId)))
            throw new RoleAlreadyExistsException(roleRequestDTO.getRoleName() + " Role Already Exists");

        log.info("RoleService:addRole execution started");
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role Not Found for roleId " + roleId));
        if(Objects.nonNull(roleRequestDTO.getRoleName()) && roleRequestDTO.getRoleName().length()>0)
            role.setRoleName(roleRequestDTO.getRoleName());
        if(Objects.nonNull(roleRequestDTO.getRoleDescription()) && roleRequestDTO.getRoleDescription().length()>0)
            role.setRoleDescription(roleRequestDTO.getRoleDescription());
        Role roleResult = roleRepository.save(role);
        roleResponseDTO = ValueMapper.convertToDTO(roleResult);
        log.debug("RoleService:updatRole  updated role from database for id ", roleId, ValueMapper.jsonAsString(roleResponseDTO));

        log.info("RoleService:updateRole execution ended.");
        return roleResponseDTO;
    }

    @Override
    public String deleteRole(int roleId) {

        log.info("RoleService:deleteRole execution started");

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role Not Found for roleId " + roleId));
        log.debug("RoleService:deleteRole deleteting role from database for id ", roleId, ValueMapper.jsonAsString(role));
        roleRepository.deleteById(roleId);

        log.info("RoleService:deleteRole execution ended.");
        return role.getRoleName() + " Role Deleted Successfully";

    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        List<RoleResponseDTO> roleResponseDTOS = null;

        log.info("RoleService:getAllRoles execution started.");

        List<Role> roles = roleRepository.findAll();
        if(!roles.isEmpty()){
            roleResponseDTOS = roles.stream()
                    .map(ValueMapper::convertToDTO)
                    .collect(Collectors.toList());
        }
        else{
            roleResponseDTOS = Collections.emptyList();
        }
        log.debug("RoleService:getAllRoles retrieving roles from database", ValueMapper.jsonAsString(roleResponseDTOS));

        log.info("RoleService:geALlRoles execution ended.");
        return roleResponseDTOS;
    }

    @Override
    public RoleResponseDTO getRoleById(int roleId) {
        RoleResponseDTO roleResponseDTO;
        log.info("RoleService:getRoleById execution started");

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role Not Found for roleId " + roleId));
        roleResponseDTO = ValueMapper.convertToDTO(role);

        log.debug("RoleService:getRoleById retrieving role from database for id ", roleId, ValueMapper.jsonAsString(roleResponseDTO));

        log.info("RoleService:getRoleById execution ended.");
        return roleResponseDTO;
    }

    @Override
    public List<RoleResponseDTO> getRolesListByIds(Set<Integer> roleIds)
    {

        List<RoleResponseDTO> roleResponseDTOs = new ArrayList<>();
        log.info("RoleService:getRolesListByIds execution started");
        List<Role> rolesList=new ArrayList<>();
        for(int id:roleIds)
        {
            Role role = this.roleRepository.findByRoleId(id).
                    orElseThrow(()->new RoleNotFoundException("entered role id "+id+" is not present"));
            roleResponseDTOs.add(ValueMapper.convertToDTO(role));
        }
        log.info("RoleService:getRolesListByIds execution ended.");
        return roleResponseDTOs;
    }
}

