package com.virtusa.rbac.controller;

import com.virtusa.rbac.dto.APIResponse;
import com.virtusa.rbac.dto.RoleRequestDTO;
import com.virtusa.rbac.dto.RoleResponseDTO;
import com.virtusa.rbac.mapper.ValueMapper;
import com.virtusa.rbac.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/roles")
public class RoleController {

    public static final String SUCCESS = "Success";
    private final RoleService roleService;

    @PostMapping("/")
    public ResponseEntity<APIResponse> createNewRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {

        log.info("RoleController::createNewRole request body ", ValueMapper.jsonAsString(roleRequestDTO));

        RoleResponseDTO roleResponseDTO = roleService.addRole(roleRequestDTO);

        APIResponse<RoleResponseDTO> responseDTO = APIResponse
                .<RoleResponseDTO>builder()
                .status(SUCCESS)
                .results(roleResponseDTO)
                .build();

        log.info("RoleController::createNewRole response ", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<APIResponse> getRoles() {

        List<RoleResponseDTO> roles = roleService.getAllRoles();

        APIResponse<List<RoleResponseDTO>> responseDTO = APIResponse
                .<List<RoleResponseDTO>>builder()
                .status(SUCCESS)
                .results(roles)
                .build();

        log.info("RolesController::getRoles response", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<?> getRoles(@PathVariable int roleId) {

        log.info("RoleController::getRoles by roleId", roleId);

        RoleResponseDTO roleResponseDTO = roleService.getRoleById(roleId);
        APIResponse<RoleResponseDTO> responseDTO = APIResponse
                .<RoleResponseDTO>builder()
                .status(SUCCESS)
                .results(roleResponseDTO)
                .build();

        log.info("RoleController::getRoles by roleId", roleId,ValueMapper
                .jsonAsString(roleResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable int roleId) {

        log.info("RoleController::deleteRole by roleId", roleId);

        String response = roleService.deleteRole(roleId);
        APIResponse<String> responseDTO = APIResponse
                .<String>builder()
                .status(SUCCESS)
                .results(response)
                .build();

        log.info("RoleController::deleteRole by roleId", roleId,response);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> updateRole(@RequestBody RoleRequestDTO roleRequestDTO, @PathVariable int roleId) {

        log.info("RoleController::updateRole by roleId", roleId);

        RoleResponseDTO roleResponseDTO = roleService.updateRole(roleRequestDTO, roleId);
        APIResponse<RoleResponseDTO> responseDTO = APIResponse
                .<RoleResponseDTO>builder()
                .status(SUCCESS)
                .results(roleResponseDTO)
                .build();

        log.info("RoleController::updateRole by roleId", roleId,ValueMapper
                .jsonAsString(roleResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/listOfRoles")
    public ResponseEntity<?> getListOfRoles(@RequestBody Map<String, Set<Integer>> roleIds) {

        log.info("RoleController::getListOfRoles by roleIds", roleIds);

        List<RoleResponseDTO> roleResponseDTOs = roleService.getRolesListByIds(roleIds.get("roleIds"));
        APIResponse<List<RoleResponseDTO>> responseDTO = APIResponse
                .<List<RoleResponseDTO>>builder()
                .status(SUCCESS)
                .results(roleResponseDTOs)
                .build();

        log.info("RoleController::getListOfRoles by roleIds", roleIds,ValueMapper
                .jsonAsString(roleResponseDTOs));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}