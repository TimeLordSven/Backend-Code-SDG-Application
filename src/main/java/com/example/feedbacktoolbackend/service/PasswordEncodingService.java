package com.example.feedbacktoolbackend.service;

import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordEncodingService {
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncodingService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Validates if passwords match during registration.
     *
     * @param password       Password provided during registration
     * @param verifyPassword Password verification provided during registration
     */
    public void validateInputPasswordRegister(String password, String verifyPassword) {
        if (!password.equals(verifyPassword)) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
    }

    /**
     * Encodes the provided raw password.
     *
     * @param rawPassword The raw password to be encoded
     * @return Encoded password
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
