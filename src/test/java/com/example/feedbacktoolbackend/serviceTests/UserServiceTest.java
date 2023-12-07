package com.example.feedbacktoolbackend.serviceTests;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
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
        RegistrationDTO dto = new RegistrationDTO(
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
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham1",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!"

        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(registrationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid prefix.")

    void testRegistrationInvalidPrefix() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van50",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(registrationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid last name.")
    void testRegistrationInvalidLastName() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van",
                "Helsing40",
                "VanHelsing@Hva.cem",
                "Password123!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(registrationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid email format.")
    void testRegistrationInvalidEmail() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hvadem",
                "Password123!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(registrationDTO));
    }
    @Test
    @Description("Registration failure due to an invalid password.")
    void testRegistrationInvalidPassword() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Pass!",
                "Password123!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(registrationDTO));
    }
    @Test

    void testRegistrationInvalidVerifyPassword() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Pa!"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(registrationDTO));
    }
}

