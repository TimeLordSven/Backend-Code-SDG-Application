package com.example.feedbacktoolbackend.controllerTests;


import com.example.feedbacktoolbackend.controller.SessionController;
import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

class SessionControllerTest {
    @Mock
    private SessionService sessionService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private SessionController sessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin_Successful() throws CustomHttpException, InvalidInputException {
        LoginDTO loginDTO = new LoginDTO("username", "password");
        String sessionId = "someSessionId";
        when(sessionService.login(loginDTO)).thenReturn(sessionId);

        ResponseEntity<Object> response = sessionController.login(loginDTO, httpServletResponse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Map.of("message", "Successful login"), response.getBody());

        // Verify that the cookie is added with the expected parameters
        verify(httpServletResponse, times(1)).addCookie(any(Cookie.class));
    }

    @Test
    void testLogin_CustomHttpException() throws CustomHttpException, InvalidInputException {
        LoginDTO loginDTO = new LoginDTO("invalid_username", "password");
        CustomHttpException customHttpException = new CustomHttpException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        when(sessionService.login(loginDTO)).thenThrow(customHttpException);

        ResponseEntity<Object> response = sessionController.login(loginDTO, httpServletResponse);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(Map.of("message", "Invalid credentials"), response.getBody());
    }

    @Test
    void testLogin_InvalidInputException() throws CustomHttpException, InvalidInputException {
        LoginDTO loginDTO = new LoginDTO(null, "password");
        InvalidInputException invalidInputException = new InvalidInputException("Invalid input");
        when(sessionService.login(loginDTO)).thenThrow(invalidInputException);

        ResponseEntity<Object> response = sessionController.login(loginDTO, httpServletResponse);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Map.of("message", "Invalid input"), response.getBody());
    }
}



