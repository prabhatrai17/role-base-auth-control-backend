package com.virtusa.rbac.serviceImpl;

import com.virtusa.rbac.dto.ApplicationDetailsRequestDto;
import com.virtusa.rbac.dto.ApplicationDetailsResponseDto;
import com.virtusa.rbac.entity.ApplicationDetails;
import com.virtusa.rbac.exception.ApplicationIsAlreadyPresentException;
import com.virtusa.rbac.exception.ApplicationNotFoundException;
import com.virtusa.rbac.repository.ApplicationDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
/* import org.springframework.security.access.prepost.PreAuthorize; */
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ApplicationDetailsService {
    @Autowired
    ApplicationDetailsRepository applicationDetailsRepository;

    @Autowired
    ModelMapper modelMapper;
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean addApplication(ApplicationDetailsRequestDto applicationDetailsRequestDto)  {
        ApplicationDetails applicationDetails=modelMapper.map(applicationDetailsRequestDto,ApplicationDetails.class);
        if(applicationDetailsRepository.findByApplicationName(applicationDetails.getApplicationName()).isEmpty()){
            applicationDetailsRepository.save(applicationDetails);
            log.info("added application details successfully");
            return true;
        }
        log.error("application is already present exception is thrown");
        throw new ApplicationIsAlreadyPresentException(applicationDetails.getApplicationName()+" application is already present");
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean updateApplication(ApplicationDetailsRequestDto applicationDetailsRequestDto,int applicationId) {
        ApplicationDetails applicationDetails=modelMapper.map(applicationDetailsRequestDto,ApplicationDetails.class);

        ApplicationDetails applicationDetails1=applicationDetailsRepository.findById(applicationId)
                .orElseThrow(()-> new ApplicationNotFoundException(applicationId+" is not found"));

            applicationDetails1.setApplicationName(applicationDetails.getApplicationName());
            applicationDetails1.setApplicationURL(applicationDetails.getApplicationURL());
            applicationDetails1.setApplicationDescription(applicationDetails.getApplicationDescription());
            applicationDetailsRepository.save(applicationDetails1);
            log.info("updated application details successfully");
            return true;

    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean deleteApplication(int applicationId)  {
        Optional<ApplicationDetails> applicationDetails1=applicationDetailsRepository.findById(applicationId);
        if(applicationDetails1.isPresent()){
            applicationDetailsRepository.delete(applicationDetails1.get());
            log.info("deleted application details successfully");
            return true;
        }
        log.error("application is not found exception is thrown");
        throw new ApplicationNotFoundException(applicationId+" is not found");
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<ApplicationDetailsResponseDto>  getApplications(){
        List<ApplicationDetails> applicationDetailsList=applicationDetailsRepository.findAll();
        List<ApplicationDetailsResponseDto> applicationDetailsResponseDtoList=new ArrayList<>();
        for(ApplicationDetails applicationDetails:applicationDetailsList){
            applicationDetailsResponseDtoList.add(modelMapper.map(applicationDetails,ApplicationDetailsResponseDto.class));
        }
        return applicationDetailsResponseDtoList;
    }

    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public ApplicationDetailsResponseDto getApplicationById(int applicationId){
        ApplicationDetails applicationDetails=applicationDetailsRepository.findById(applicationId)
        .orElseThrow(()-> new ApplicationNotFoundException(applicationId+" is not found"));
        return modelMapper.map(applicationDetails,ApplicationDetailsResponseDto.class);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public List<ApplicationDetails> getApplicationListByIds(List<Integer> applicationIdList){
//        List<ApplicationDetails> applicationList=new ArrayList<>();
//        for(int id:applicationIdList){
//            applicationList.add(getApplicationById(id));
//        }
//        return applicationList;
//    }
}
