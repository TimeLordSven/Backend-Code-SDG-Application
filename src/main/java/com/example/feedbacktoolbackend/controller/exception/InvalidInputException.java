package com.example.feedbacktoolbackend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown for invalid input data.
 * Extends RuntimeException and sets the HTTP status to BAD_REQUEST.
 * @author Sven Molenaar
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
    /**
     * Constructs an InvalidInputException with the provided error message.
     * @param message Error message indicating the reason for the invalid input
     */
    public InvalidInputException(String message) {
        super("sError: " + message);
    }
}