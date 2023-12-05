package com.example.feedbacktoolbackend.serviceTests;

import com.example.feedbacktoolbackend.controller.dto.AuthenticationDTO;
import com.example.feedbacktoolbackend.controller.exception.*;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testRegistrationValidEverything() {
        AuthenticationDTO dto = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!",
                "STUDENT");
        UserBusiness user = userService.createUser(dto);
        Assertions.assertNotNull(user);
    }
    @Test
    void testRegistrationInvalidFirstName() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham1",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidPrefix() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van50",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidLastName() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing40",
                "VanHelsing@Hva.cem",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidEmail() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hvadem",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidPassword() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Pass!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidVerifyPassword() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Pa!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
}

