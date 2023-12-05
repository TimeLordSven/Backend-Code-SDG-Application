package com.example.feedbacktoolbackend.serviceTests;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.AuthenticationDTO;
import com.example.feedbacktoolbackend.controller.exception.*;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import jdk.jfr.Description;
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
    @Description("Successful registration with valid user details.")
    void testRegistrationValidEverything() {
        AuthenticationDTO dto = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!"
        );
        UserBusiness user = userService.createUser(dto);
        Assertions.assertNotNull(user);
    }
    @Test
    @Description("Registration failure due to an invalid first name.")
    void testRegistrationInvalidFirstName() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham1",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!"

        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid prefix.")

    void testRegistrationInvalidPrefix() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van50",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid last name.")
    void testRegistrationInvalidLastName() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing40",
                "VanHelsing@Hva.cem",
                "Password123!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid email format.")
    void testRegistrationInvalidEmail() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hvadem",
                "Password123!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid password.")
    void testRegistrationInvalidPassword() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Pass!",
                "Password123!"
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
                "Pa!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
}

