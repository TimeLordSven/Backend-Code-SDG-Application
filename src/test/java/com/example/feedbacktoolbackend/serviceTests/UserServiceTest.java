package com.example.feedbacktoolbackend.serviceTests;

import com.example.feedbacktoolbackend.controller.dto.AuthenticationDTO;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.service.PasswordEncodingService;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testRegistrationValidEverything() {
        AuthenticationDTO dto = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!",
                "STUDENT");
        UserBusiness user = userService.createUser(dto);
        Assertions.assertNotNull(user);
    }
    @Test
    void testRegistrationInvalidFirstName() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham1",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidPrefix() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van50",
                "Helsing",
                "VanHelsing@Hva.com",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidLastName() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing40",
                "VanHelsing@Hva.cem",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidEmail() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hvadem",
                "Password123!",
                "Password123!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
    @Test
    void testRegistrationInvalidPassword() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                "Abraham",
                "Van",
                "Helsing",
                "VanHelsing@Hva.com",
                "Pass!",
                "Password123!",
                "STUDENT"
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
                "Pa!",
                "STUDENT"
        );
        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
    }
}
//@SpringBootTest
//class UserServiceTest {
//
//    @Mock
//    private PasswordEncodingService passwordEncoderService;
//
//    @Mock
//    private UserRepository repository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//    }
//    @Test
//    void testRegistrationValidEverything1() {
//        when(passwordEncoderService.encodePassword(any())).thenReturn("encodedPassword");
//        when(repository.existsByEmail(any())).thenReturn(false);
//        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
//                "Hva.com",
//                "Abraham",
//                "Van",
//                "VanHelsing@Hva.com",
//                "Password123!",
//                "Password123!",
//                "STUDENT"
//        );
//
//        // Use assertDoesNotThrow without specifying exception type
//        assertDoesNotThrow(() -> userService.createUser(authenticationDTO));
//    }
//}
//    @Test
//    void testRegistrationValidEverything2() {
//        when(passwordEncoderService.encodePassword(any())).thenReturn("encodedPassword");
//        when(repository.existsByEmail(any())).thenReturn(false);
//        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
//                "Hva.com",
//                "Abraham",
//                "Van",
//                "VanHelsing@Hva.com",
//                "Password123!",
//                "Password123!",
//                "STUDENT"
//        );
//        assertDoesNotThrow(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
//    }
//    @Test
//    void testRegistrationInvalidEmail2() {
//        when(passwordEncoderService.encodePassword(any())).thenReturn("encodedPassword");
//        when(repository.existsByEmail(any())).thenReturn(false);
//        AuthenticationDTO authenticationDTO = new AuthenticationDTO(
//                "Abraham",
//                "Van",
//                "Helsing",
//                "VanHelsing@Hvadem",
//                "Password123!",
//                "Password123!",
//                "STUDENT"
//        );
//        assertThrows(InvalidInputException.class, () -> userService.createUser(authenticationDTO));
//    }

//}



