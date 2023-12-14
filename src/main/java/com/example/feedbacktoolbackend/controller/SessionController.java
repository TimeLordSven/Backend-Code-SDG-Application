package com.example.feedbacktoolbackend.controller;

import com.example.feedbacktoolbackend.controller.dto.LoginDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        try {
            String sessionId = sessionService.login(loginDTO);

            int twoWeeksInSeconds = 1_209_600;

            Cookie sessionCookie = new Cookie("session_id", sessionId);
            sessionCookie.setMaxAge(twoWeeksInSeconds);
            response.addCookie(sessionCookie);

            return new ResponseEntity<>(Map.of("message", "Successful login"), HttpStatus.OK);
        } catch (CustomHttpException | InvalidInputException e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            if (e instanceof CustomHttpException) {
                httpStatus = ((CustomHttpException) e).getHttpStatus();
            }
            return new ResponseEntity<>(Map.of("message", e.getMessage()), httpStatus);
        }
    }
}
