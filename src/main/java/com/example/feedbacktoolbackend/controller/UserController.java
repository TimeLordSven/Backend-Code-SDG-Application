package com.example.feedbacktoolbackend.controller;

import com.example.feedbacktoolbackend.controller.dto.AuthenticationDTO;
import com.example.feedbacktoolbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody AuthenticationDTO requestBody) {
        userService.createUser(requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}