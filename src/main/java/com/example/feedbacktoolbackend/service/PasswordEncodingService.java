package com.example.feedbacktoolbackend.service;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PasswordEncodingService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final String passwordRegex;

    public PasswordEncodingService() {
        passwordEncoder = new BCryptPasswordEncoder();
        passwordRegex = "^(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]}])" +   "(.{8,})$";
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matchPasswords(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    public boolean validatePassword(String password) {
        return password.matches(passwordRegex);
    }
}
