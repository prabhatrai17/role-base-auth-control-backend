package com.virtusa.rbac.controller;


import com.virtusa.rbac.dto.ApplicationDetailsRequestDto;
import com.virtusa.rbac.dto.ApplicationDetailsResponseDto;
import com.virtusa.rbac.entity.ApplicationDetails;
import com.virtusa.rbac.serviceImpl.ApplicationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicationDetailsAPI")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ApplicationDetailsController {
    @Autowired
    ApplicationDetailsService applicationDetailsService;

    @PostMapping
    public boolean addApplicationDetails(@RequestBody ApplicationDetailsRequestDto applicationDetailsRequestDto)  {
        return applicationDetailsService.addApplication(applicationDetailsRequestDto);
    }
    @GetMapping
    public List<ApplicationDetailsResponseDto> getApplicationDetails(){

        return applicationDetailsService.getApplications();
    }

    @PutMapping("/{applicationId}")
    public boolean updateApplicationDetails(@RequestBody ApplicationDetailsRequestDto applicationDetailsRequestDto,@PathVariable int applicationId) {
        return applicationDetailsService.updateApplication(applicationDetailsRequestDto,applicationId);
    }

    @DeleteMapping("/{applicationId}")
    public boolean deleteApplicationDetails(@PathVariable int applicationId) {
        return applicationDetailsService.deleteApplication(applicationId);
    }

    @GetMapping("{applicationId}")
    public ApplicationDetailsResponseDto getApplicationDetailsById(@PathVariable int applicationId){
        return applicationDetailsService.getApplicationById(applicationId);
    }

}
