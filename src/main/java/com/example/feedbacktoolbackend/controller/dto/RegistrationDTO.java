package com.example.feedbacktoolbackend.controller.dto;
/**
 * Represents a Data Transfer Object (DTO) for user information.
 * This record encapsulates user data, providing a concise and immutable representation.
 * @author Sven Molenaar
 */
public record RegistrationDTO(String firstName, String prefixes, String lastName, String email, String password, String verifyPassword) {

}
