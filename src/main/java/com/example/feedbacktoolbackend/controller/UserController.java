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

import java.util.Map;

/**
 * Controller handling user-related endpoints for students.
 * Manages user creation and validation operations.
 *
 * @author Sven Molenaar
 */
@RestController
@RequestMapping("/users/students")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for creating a new user (student).
     *
     * @param requestBody The RegistrationDTO containing user details in the request body
     * @return ResponseEntity containing the status of the user creation operation
     * @throws InvalidInputException If invalid input data is encountered during user creation
     * @throws CustomHttpException   If a custom HTTP exception occurs during user creation
     * @author Sven Molenaar
     */
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

    /**
     * Exception handler for handling validation errors in the request body.
     *
     * @param ex The MethodArgumentNotValidException thrown during validation failure
     * @return ResponseEntity containing the error message for the validation failure
     * @author Sven Molenaar
     */
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
