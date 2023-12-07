package com.example.feedbacktoolbackend.service;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for password encoding, matching, and validation using BCrypt.
 * @author Sven Molenaar
 */
@Service
@Transactional
public class PasswordEncodingService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final String passwordRegex;

    /**
     * Constructor initializing BCryptPasswordEncoder and setting the password regex pattern.
     * @Author Sven Molenaar
     */
    public PasswordEncodingService() {
        passwordEncoder = new BCryptPasswordEncoder();
        passwordRegex = "^(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]}])(?=.*[a-zA-Z0-9]).{8,}$";

    }

    /**
     * Encodes the given raw password using BCryptPasswordEncoder.
     * @author Sven Molenaar
     * @param rawPassword The password to be encoded
     * @return Encoded password
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Matches the given raw password with the encoded password using BCrypt.
     * @author Sven Molenaar
     * @param rawPassword      The raw password to compare
     * @param encodedPassword  The encoded password to compare against
     * @return True if passwords match, otherwise false
     */
    public boolean matchPasswords(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Validates the given password against a predefined regex pattern.
     * @author Sven Molenaar
     * @param password The password to validate
     * @return True if the password meets the criteria specified by the regex, otherwise false
     */
    public boolean validatePassword(String password) {
        return password.matches(passwordRegex);
    }
}



