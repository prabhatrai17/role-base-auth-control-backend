package com.virtusa.rbac.service;



import com.virtusa.rbac.dto.RoleRequestDTO;
import com.virtusa.rbac.dto.RoleResponseDTO;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface RoleService {
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    RoleResponseDTO addRole(RoleRequestDTO roleRequestDTO);
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    RoleResponseDTO updateRole(RoleRequestDTO roleRequestDTO, int roleId);
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    String deleteRole(int roleId);
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<RoleResponseDTO> getAllRoles();
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    RoleResponseDTO getRoleById(int roleId);
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<RoleResponseDTO> getRolesListByIds(Set<Integer> roleIds);
}