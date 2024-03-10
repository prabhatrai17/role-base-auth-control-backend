package com.virtusa.rbac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    String profileName;
    String profileDescription;
    Set<Integer> roleIds;


}
