package com.example.feedbacktoolbackend.serviceTests;

import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.service.PasswordEncodingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncodingServiceTest {

    private PasswordEncodingService passwordEncodingService;

    @BeforeEach
    void setUp() {
        passwordEncodingService = new PasswordEncodingService();
    }

    @Test
    void validateInputPasswordRegister_PasswordsMatch_ShouldNotThrowException() {
        assertDoesNotThrow(() -> {
            passwordEncodingService.validateInputPasswordRegister("Password12!", "Password12!");
        });
    }

    @Test
    void validateInputPasswordRegister_PasswordsDoNotMatch_ShouldThrowCustomHttpException() {
        CustomHttpException exception = assertThrows(CustomHttpException.class, () -> {
            passwordEncodingService.validateInputPasswordRegister("Password12!", "Password123!");
        });

        assertEquals("Passwords do not match", exception.getMessage());
    }

    @Test
    void encodePassword_ShouldReturnEncodedPassword() {
        String rawPassword = "Password12!";
        String encodedPassword = passwordEncodingService.encodePassword(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }
}
