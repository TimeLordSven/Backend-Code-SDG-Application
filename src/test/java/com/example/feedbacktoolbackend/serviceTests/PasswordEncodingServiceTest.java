/**
 * @Author Sven Molenaar
 */
package com.example.feedbacktoolbackend.serviceTests;

import com.example.feedbacktoolbackend.service.PasswordEncodingService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncodingServiceTest {

    /**
     * Test to verify the encoding of a password using PasswordEncodingService.
     * Verifies that the encoded password is not equal to the raw password and matches the raw password when checked with BCryptPasswordEncoder.
     * @author Sven Molenaar
     */
    @Test
    void testEncodePassword() {
        PasswordEncodingService passwordEncodingService = new PasswordEncodingService();
        String rawPassword = "Password123!";
        String encodedPassword = passwordEncodingService.encodePassword(rawPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, encodedPassword));
    }

    /**
     * Test to check the matching of passwords using PasswordEncodingService.
     * Verifies that a raw password matches its encoded version and that a different password doesn't match the encoded password.
     * @author Sven Molenaar
     */
    @Test
    void testMatchPasswords() {
        PasswordEncodingService passwordEncodingService = new PasswordEncodingService();
        String rawPassword = "Password123!";
        String encodedPassword = passwordEncodingService.encodePassword(rawPassword);
        assertTrue(passwordEncodingService.matchPasswords(rawPassword, encodedPassword));
        assertFalse(passwordEncodingService.matchPasswords("Wrongpassword123!", encodedPassword));
    }

    /**
     * Test to validate the strength of a password using PasswordEncodingService.
     * Verifies that a password meets the required criteria (contains special characters and has a minimum length) to be considered valid.
     * Verifies that a password without special characters is considered invalid.
     * @author Sven Molenaar
     */
    @Test
    void testValidatePassword() {
        PasswordEncodingService passwordEncodingService = new PasswordEncodingService();
        assertTrue(passwordEncodingService.validatePassword("Valid@Password123"));
        assertFalse(passwordEncodingService.validatePassword("weakpassword")); // Missing special characters
    }
}
