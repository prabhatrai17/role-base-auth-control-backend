package com.virtusa.rbac.service;

import com.virtusa.rbac.dto.ApplicationDetailsRequestDto;
import com.virtusa.rbac.dto.ApplicationDetailsResponseDto;
import com.virtusa.rbac.entity.ApplicationDetails;
import com.virtusa.rbac.exception.ApplicationIsAlreadyPresentException;
import com.virtusa.rbac.exception.ApplicationNotFoundException;

import com.virtusa.rbac.repository.ApplicationDetailsRepository;
import com.virtusa.rbac.serviceImpl.ApplicationDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationDetailsServiceTests {
    @MockBean
    ApplicationDetailsRepository applicationDetailsRepository;

    @Autowired
    ApplicationDetailsService applicationDetailsService;

    @Autowired
    ModelMapper modelMapper;

    ApplicationDetailsRequestDto applicationDetailsRequestDto;
    ApplicationDetails applicationDetails;

    @BeforeEach
    public void init(){
         applicationDetails=ApplicationDetails
                .builder()
                .applicationId(2)
                .applicationURL("http://localhost:9000/velocity")
                .applicationName("velocity5")
                .applicationDescription("user details info")
                .build();
        applicationDetailsRequestDto=modelMapper.map(applicationDetails,ApplicationDetailsRequestDto.class);
    }

    @Test
    public void testAddApplication(){
        when(applicationDetailsRepository.save(applicationDetails)).thenReturn(applicationDetails);
        assertEquals(true,applicationDetailsService.addApplication(applicationDetailsRequestDto));

    }

    @Test
    public void testAddExsistingApplication(){

        when(applicationDetailsRepository.findByApplicationName(applicationDetails.getApplicationName()))
                .thenReturn(Optional.ofNullable(applicationDetails));

        assertThrows(ApplicationIsAlreadyPresentException.class,
                ()->applicationDetailsService.addApplication(applicationDetailsRequestDto));
    }

    @Test
    public void testGetApplications(){
        List<ApplicationDetails> appList=new ArrayList<>();
        appList.add(applicationDetails);
        when(applicationDetailsRepository.findAll())
                .thenReturn(appList);

        assertEquals(appList.size(),applicationDetailsService.getApplications().size());
    }

    @Test
    public void testGetApplicationById(){
        when(applicationDetailsRepository.findById(applicationDetails.getApplicationId()))
                .thenReturn(Optional.ofNullable(applicationDetails));
        assertEquals(modelMapper.map(applicationDetails,ApplicationDetailsResponseDto.class),
                applicationDetailsService.getApplicationById(applicationDetails.getApplicationId()));

    }

    @Test
    public void testEditApplication(){
        when(applicationDetailsRepository.findById(applicationDetails.getApplicationId()))
                .thenReturn(Optional.ofNullable(applicationDetails));
        applicationDetails.setApplicationName("velocity");
        when(applicationDetailsRepository.save(applicationDetails)).thenReturn(applicationDetails);
        assertEquals(true,applicationDetailsService.updateApplication(applicationDetailsRequestDto,applicationDetails.getApplicationId()));
    }

    @Test
    public void testEditApplicationWhenIdNotPresent(){
        applicationDetails.setApplicationName("velocity");
        when(applicationDetailsRepository.save(applicationDetails)).thenReturn(applicationDetails);
       assertThrows(ApplicationNotFoundException.class,
               ()->applicationDetailsService.updateApplication(applicationDetailsRequestDto, applicationDetails.getApplicationId()));
    }

    @Test
    public void testDeleteApplication(){
        when(applicationDetailsRepository.findById(applicationDetails.getApplicationId()))
                .thenReturn(Optional.ofNullable(applicationDetails));
        assertEquals(true,applicationDetailsService.deleteApplication(applicationDetails.getApplicationId()));

    }

    @Test
    public void testDeleteApplicationWhenNoIdPresent(){
        assertThrows(ApplicationNotFoundException.class,
                ()->applicationDetailsService.deleteApplication(applicationDetails.getApplicationId()));

    }


}
