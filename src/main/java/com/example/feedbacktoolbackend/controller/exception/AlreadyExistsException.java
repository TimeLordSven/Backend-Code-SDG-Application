package com.example.feedbacktoolbackend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an entity already exists.
 * Extends RuntimeException and sets the HTTP status to BAD_REQUEST.
 * @author Sven Molenaar
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {
    /**
     * Constructs an AlreadyExistsException with the provided entity name.
     * @param entityName Name of the entity that already exists.
     */
    public AlreadyExistsException(String entityName) {
        super(entityName + " already exists.");
    }
}
