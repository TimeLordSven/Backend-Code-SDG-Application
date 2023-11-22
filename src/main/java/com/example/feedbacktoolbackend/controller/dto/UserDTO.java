package com.example.feedbacktoolbackend.controller.dto;
/**
 * Represents a Data Transfer Object (DTO) for user information.
 * This record encapsulates user data, providing a concise and immutable representation.
 * @author Sven Molenaar
 */
public record UserDTO(Long id, String firstName, String lastName) {
}
