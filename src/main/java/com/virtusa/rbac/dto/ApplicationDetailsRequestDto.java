package com.virtusa.rbac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDetailsRequestDto {
    String applicationName;
    String applicationDescription;
    String applicationURL;
}
