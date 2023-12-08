package com.example.feedbacktoolbackend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown for invalid input data.
 * Extends RuntimeException and allows setting a custom HTTP status code.
 *
 * @author Sven Molenaar
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomHttpException extends RuntimeException {
    private final HttpStatus httpStatus;

    /**
     * Constructs a CustomHttpException with the provided HTTP status and error message.
     *
     * @param httpStatus Custom HTTP status code to be sent back
     * @param message    Error message indicating the reason for the exception
     * @author Sven Molenaar
     */
    public CustomHttpException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * Get the HTTP status code associated with this exception.
     *
     * @return The HTTP status code
     * @author Sven Molenaar
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}