package com.example.feedbacktoolbackend.controller;

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/Sessions")
public class SessionController {
    private final SessionService sessiomService;


    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody RegistrationDTO requestBody) {
        try {
            userService.createUser(requestBody);
            return new ResponseEntity<>(Map.of("message", "Successfully created"), HttpStatus.CREATED);
        } catch (InvalidInputException | CustomHttpException e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            if (e instanceof CustomHttpException) {
                httpStatus = ((CustomHttpException) e).getHttpStatus();
            }
            return new ResponseEntity<>(Map.of("message", e.getMessage()), httpStatus);
        }
    }

}
