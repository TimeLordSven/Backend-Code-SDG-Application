package com.example.feedbacktoolbackend.serviceTests;
/*
  @Author Sven Molenaar
 */

import com.example.feedbacktoolbackend.controller.SessionController;
import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
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
import static org.mockito.Mockito.when;

class SessionServiceTest {
    @Mock
    private SessionService sessionService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private SessionController sessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_SuccessfulLogin_Returns200AndCookie() {
        LoginDTO loginDTO = new LoginDTO(
                "VanHelsing@Hva.com",
                "Password123!"
        );
        when(sessionService.login(loginDTO)).thenReturn("sessionId");

        ResponseEntity<Object> responseEntity = sessionController.login(loginDTO, response);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(response, times(1)).addCookie(any(Cookie.class));
    }

    @Test
    void login_WrongPassword_Returns401WithError() {
        LoginDTO loginDTO = new LoginDTO(

                "VanHelsing@Hva.com",
                "WrongPassword123!"

        );
        when(sessionService.login(loginDTO)).thenThrow(new CustomHttpException(HttpStatus.UNAUTHORIZED, "The email and password do not match"));

        ResponseEntity<Object> responseEntity = sessionController.login(loginDTO, response);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("The email and password do not match", ((Map<?, ?>) responseEntity.getBody()).get("message"));
    }

    @Test
    void login_EmptyInput_Returns400WithError() {
        LoginDTO loginDTO = new LoginDTO(

                "",
                ""
        );
        when(sessionService.login(loginDTO)).thenThrow(new CustomHttpException(HttpStatus.BAD_REQUEST, "Input cannot be empty"));

        ResponseEntity<Object> responseEntity = sessionController.login(loginDTO, response);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Input cannot be empty", ((Map<?, ?>) responseEntity.getBody()).get("message"));
    }

    @Test
    void login_InvalidEmail_Returns400WithError() {
        LoginDTO loginDTO = new LoginDTO(

                "VanHelsingHva.com",
                "Password123!"
        );
        when(sessionService.login(loginDTO)).thenThrow(new CustomHttpException(HttpStatus.BAD_REQUEST, "The email is not valid"));

        ResponseEntity<Object> responseEntity = sessionController.login(loginDTO, response);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The email is not valid", ((Map<?, ?>) responseEntity.getBody()).get("message"));
    }
}

