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

/**
 * Controller handling user-related operations.
 * Maps endpoints related to student user creation.
 * @author Sven Molenaar
 * Parameters (userService, requestBody)
 * Returns ResponseEntity with HTTP status
 */
@RestController
@RequestMapping("/users/students")
public class UserController {
    private final UserService userService;

    /**
     * Constructor injection for UserService.
     * @author Sven Molenaar
     * @param userService Service handling user operations
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    /**
//     * Endpoint to create a new student user.
//     * Receives an RegistrationDTO from the request body.
//     * Calls the UserService to create a new user.
//     * @author Sven Molenaar
//     * @param requestBody RegistrationDTO object containing user data
//     * Returns ResponseEntity with CREATED status
//     */
//    @PostMapping
//    public ResponseEntity<Object> createUser(@RequestBody RegistrationDTO requestBody) {
//        try {
//            userService.createUser(requestBody);
//            return new ResponseEntity<>(Map.of("message", "Successfully created"), HttpStatus.CREATED);
//        } catch (InvalidInputException | CustomHttpException e) {
//            // Handle the specific exception
//            return new ResponseEntity<>(Map.of("error", "Exception occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//    }
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody RegistrationDTO requestBody) {
        try {
            userService.createUser(requestBody);
            return new ResponseEntity<>(Map.of("message", "Successfully created"), HttpStatus.CREATED);
        } catch (InvalidInputException | CustomHttpException e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // Default status
            if (e instanceof CustomHttpException) {
                httpStatus = ((CustomHttpException) e).getHttpStatus();
            }

            return new ResponseEntity<>(Map.of("error", "message: " + e.getMessage()), httpStatus);
        }
    }

}
