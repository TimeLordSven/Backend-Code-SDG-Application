package com.example.feedbacktoolbackend.controller;

import com.example.feedbacktoolbackend.controller.dto.RegistrationDTO;
import com.example.feedbacktoolbackend.controller.exception.CustomHttpException;
import com.example.feedbacktoolbackend.controller.exception.InvalidInputException;
import com.example.feedbacktoolbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users/students")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid RegistrationDTO requestBody) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

        String errorMessage = "Validation failed";
        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }

        return new ResponseEntity<>(Map.of("message", errorMessage), HttpStatus.BAD_REQUEST);
    }




}
