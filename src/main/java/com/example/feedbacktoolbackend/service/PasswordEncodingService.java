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

    public void validatePassword(String password, String verifyPassword) {
        if (password.length() < 8) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The password doesn't meet the required length");
        }
        if (!password.matches(".*[!@#$%^&*()_+{}|:\"<>?,./;'\\[\\]`~].*")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The Password should have at least 1 special character");
        }
        if (password.contains(" ")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The Password can't contain any spaces");
        }
        if (!password.equals(verifyPassword)) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
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



