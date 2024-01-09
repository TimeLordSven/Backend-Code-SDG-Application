package com.example.feedbacktoolbackend.serviceTests;

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @Description("Successful registration with valid user details")
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
        assertNotNull(user);
    }


    @Test
    @Description("Registration failure due to an invalid password")
    void testRegistrationInvalidPassword() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Pass!",
                "Password123!"
        );
        assertThrows(CustomHttpException.class, () -> userService.createUser(registrationDTO));
    }

    @Test
    @Description("Invalid verified password during registration")
    void testRegistrationInvalidVerifyPassword() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Pa!"
        );
        assertThrows(CustomHttpException.class, () -> userService.createUser(registrationDTO));
    }
}
