package com.example.feedbacktoolbackend.serviceTests;
/*
  @author Sven Molenaar
 */

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

    /**
     * Validates that a password below the required length throws a CustomHttpException.
     *
     * @author Sven Molenaar
     */
    @Test
    void validatePassword_LengthBelowRequirement_ShouldThrowHttpException() {
        assertThrows(CustomHttpException.class, () -> {
            passwordEncodingService.validateInputPasswordRegister("pass", "pass");
        });
    }

    /**
     * Validates that a password without special characters throws a CustomHttpException.
     *
     * @author Sven Molenaar
     */
    @Test
    void validatePassword_NoSpecialCharacter_ShouldThrowHttpException() {
        assertThrows(CustomHttpException.class, () -> {
            passwordEncodingService.validateInputPasswordRegister("Password123", "Password123");
        });
    }

    /**
     * Validates that a password containing spaces throws a CustomHttpException.
     *
     * @author Sven Molenaar
     */
    @Test
    void validatePassword_ContainsSpace_ShouldThrowHttpException() {
        assertThrows(CustomHttpException.class, () -> {
            passwordEncodingService.validateInputPasswordRegister("Pass word123!", "Pass word123!");
        });
    }

    /**
     * Validates that non-matching passwords throw a CustomHttpException.
     *
     * @author Sven Molenaar
     */
    @Test
    void validatePassword_PasswordsDoNotMatch_ShouldThrowHttpException() {
        assertThrows(CustomHttpException.class, () -> {
            passwordEncodingService.validateInputPasswordRegister("Password123!", "Password321!");
        });
    }

    /**
     * Verifies that the encodePassword method returns an encoded password.
     *
     * @author Sven Molenaar
     */
    @Test
    void encodePassword_ShouldReturnEncodedPassword() {
        String rawPassword = "Password123!";
        String encodedPassword = passwordEncodingService.encodePassword(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    /**
     * Validates that a valid password passes the validatePassword method.
     *
     * @author Sven Molenaar
     */
    @Test
    void validatePassword_validatePassword_ValidPassword_ShouldReturnTrue() {
        String validPassword = "Passw0rd!";
        assertTrue(passwordEncodingService.validatePassword(validPassword));
    }
}