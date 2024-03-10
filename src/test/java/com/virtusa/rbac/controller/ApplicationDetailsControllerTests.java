package com.virtusa.rbac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.virtusa.rbac.dto.ApplicationDetailsRequestDto;
import com.virtusa.rbac.dto.ApplicationDetailsResponseDto;
import com.virtusa.rbac.entity.ApplicationDetails;
import com.virtusa.rbac.serviceImpl.ApplicationDetailsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ApplicationDetailsControllerTests {

    @Mock
    ApplicationDetailsService applicationDetailsService;

    @InjectMocks
    ApplicationDetailsController applicationDetailsController;
    @Autowired
    MockMvc mockMvc;



    ObjectMapper objectMapper;

    ModelMapper modelMapper;
     List<ApplicationDetailsResponseDto> applicationDetailsList;

    @BeforeEach
     void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(applicationDetailsController).build();
        objectMapper=new ObjectMapper();
        modelMapper=new ModelMapper();
        applicationDetailsList=new ArrayList<>();
        ApplicationDetails applicationDetails=ApplicationDetails.builder()
                .applicationId(1)
                .applicationDescription("pay roll")
                .applicationName("allsec portal")
                .applicationURL("http://localhost:9000/allsec").build();
        applicationDetailsList.add(modelMapper.map(applicationDetails,ApplicationDetailsResponseDto.class));
    }

    @Test
    public void testAddApplication() throws Exception {
        when(applicationDetailsService.addApplication
                (modelMapper.map(applicationDetailsList.get(0), ApplicationDetailsRequestDto.class)))
                .thenReturn(true);
        mockMvc.perform(
                post("/applicationDetailsAPI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationDetailsList.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testEditApplication() throws Exception {
        when(applicationDetailsService.updateApplication
                (modelMapper.map(applicationDetailsList.get(0), ApplicationDetailsRequestDto.class),1))
                .thenReturn(true);
        mockMvc.perform(
                        put("/applicationDetailsAPI/{id}",1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(applicationDetailsList.get(0))))
                        .andExpect(status().isOk())
                         .andExpect(content().string("true"));
//        .andExpect(result -> result.getResolvedException().getMessage().equals("product not found"));
    }

    @Test
    public void testGetApplications() throws Exception {
        when(applicationDetailsService.getApplications())
                .thenReturn(applicationDetailsList);
        MvcResult result=mockMvc.perform(
                        get("/applicationDetailsAPI")
                                .contentType(MediaType.APPLICATION_JSON)
                                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(applicationDetailsList),result.getResponse().getContentAsString());
    }

    @Test
    public void testGetApplicationById() throws Exception {
        when(applicationDetailsService.getApplicationById(1))
                .thenReturn(applicationDetailsList.get(0));
        MvcResult result=mockMvc.perform(
                        get("/applicationDetailsAPI/{id}",1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(applicationDetailsList.get(0)),result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteApplication() throws Exception {
        when(applicationDetailsService.deleteApplication(1))
                .thenReturn(true);
        mockMvc.perform(
                        delete("/applicationDetailsAPI/{id}",1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
