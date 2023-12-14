package com.example.feedbacktoolbackend.controllerTests;

import com.example.feedbacktoolbackend.controller.UserController;
import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser_Successful() throws CustomHttpException, InvalidInputException {
        RegistrationDTO registrationDTO = new RegistrationDTO("John", "Von", "Doe", "JohnVonDoe@Hva.com", "Password123!", "Password123!");
        // Mock the userService to avoid database interactions
        when(userService.createUser(any(RegistrationDTO.class))).thenReturn(new UserBusiness("John", "Von", "Doe", "JohnVonDoe@Hva.com", "Password123!", Role.ADMIN));
        ResponseEntity<Object> response = userController.createUser(registrationDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Map.of("message", "Successfully created"), response.getBody());
        verify(userService, times(1)).createUser(registrationDTO);
    }

    @Test
    void testCreateUser_InvalidInputException() throws CustomHttpException, InvalidInputException {
        RegistrationDTO registrationDTO = new RegistrationDTO("John", "Von", "Doe", "JohnVonDoe@Hva.com", "Password123!", "DifferentPassword");
        doThrow(new InvalidInputException("Passwords do not match")).when(userService).createUser(any(RegistrationDTO.class));
        ResponseEntity<Object> response = userController.createUser(registrationDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Map.of("message", "Passwords do not match"), response.getBody());
        verify(userService, times(1)).createUser(registrationDTO);
    }
}
