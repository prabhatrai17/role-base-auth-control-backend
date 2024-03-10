package com.virtusa.rbac.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoleRequestDTO {

    @NotBlank(message = "Role name cannot be empty")
    private String roleName;
    @NotBlank(message = "Role description cannot be empty")
    private String roleDescription;
}

