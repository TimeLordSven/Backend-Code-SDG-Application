package com.example.feedbacktoolbackend.controller.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Handles all unhandled exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles middleware validation
     *
     * @param exception the thrown exception when middleware is invalid
     * @return a response with the errors of the validation
     * @author Sven Molenaar
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String responseMessage = (result.getFieldError() != null && result.getFieldError().getDefaultMessage() != null)
                ? result.getFieldError().getDefaultMessage()
                : "Validation error";
        return buildErrorResponse(responseMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles authorization exceptions
     *
     * @param exception the thrown authorization exception
     * @return a response indicating invalid cookies and an unauthorized status
     * @author Sven Molenaar
     */
    @ExceptionHandler(AuthorisationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(AuthorisationException exception) {
        return buildErrorResponse("Invalid Cookies", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles constraint violation exceptions
     *
     * @param exception the thrown constraint violation exception
     * @return a response indicating no access rights and a forbidden status
     * @author Sven Molenaar
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException exception) {
        return buildErrorResponse("No access rights", HttpStatus.FORBIDDEN);
    }

    /**
     * Handles all the exception that are unhandled
     *
     * @param exception the thrown exception
     * @return An error message and a Internal Server Error status
     * @author Sven Molenaar
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception exception) {
        String errorMessage = "An unexpected error occurred: " + exception.getMessage();
        return buildErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds a ResponseEntity with a map containing the error message and the specified status
     *
     * @param message the error message
     * @param status  the HTTP status code
     * @return the ResponseEntity with the error message and status
     * @author Sven Molenaar
     */
    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(Map.of("message", message), status);
    }
}
