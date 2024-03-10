package com.virtusa.rbac.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private UserRepositoryTest userRepository;

    @Test
    @DisplayName("TestingFindByUsernameMethodWithParamStringUsernameShouldReturnOptionalUser")
    public void FindByUsernameMethodWithParamStringUsernameShouldReturnOptionalUser(){

    }

}
