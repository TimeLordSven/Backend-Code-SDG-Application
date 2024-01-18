package com.example.feedbacktoolbackend.controller.dto;

import jakarta.validation.constraints.*;

/**
 * Represents a Data Transfer Object (DTO) for user information.
 * This record encapsulates user data, providing a concise and immutable representation.
 *
 * @author Sven Molenaar
 */
public record RegistrationDTO(
        @NotNull(message = "First name cannot be null")
        @NotEmpty(message = "First name cannot be an empty string")
        @Size(min = 1, max = 50, message = "First name should be between 1 and 50 characters long")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "First name can only contain alphabetic characters")
        @Pattern(regexp = "^\\S+$", message = "First name cannot contain spaces")
        String firstName,

        @Size(min = 1, max = 50, message = "Prefixes should be between 1 and 50 characters long")
        String prefixes,

        @NotNull(message = "Last name cannot be null")
        @NotEmpty(message = "Last name cannot be an empty string")
        @Size(max = 50, message = "Last name should be between 1 and 50 characters long")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name can only contain alphabetic characters")
        @Pattern(regexp = "^\\S+$", message = "Last name cannot contain spaces")
        String lastName,

        @NotNull(message = "Email cannot be null")
        @NotEmpty(message = "Email cannot be an empty string")
        @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email format")
        String email,

        @NotNull(message = "Password cannot be null")
        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 8, max = 255, message = "Password should be between 8 and 255 characters long")
        @Pattern(regexp = "^(?!\\s+$)(?=.*[!@#$%^&*()_+{}|:\"<>?,./;'\\[\\]`~])(?=.*\\d).*$", message = "Password must contain at least one special character and a number")
        @Pattern(regexp = "^\\S*$", message = "Password cannot contain spaces")
        String password,

        @NotNull(message = "Verify password cannot be null")
        @NotEmpty(message = "Verify password cannot be empty")
        @Size(min = 8, max = 255, message = "Verify password should be between 8 and 255 characters long")
        @Pattern(regexp = "^(?!\\s+$)(?=.*[!@#$%^&*()_+{}|:\"<>?,./;'\\[\\]`~])(?=.*\\d).*$", message = "Verify password must contain at least one special character and a number")
        @Pattern(regexp = "^\\S*$", message = "Verify password cannot contain spaces")
        String verifyPassword
){

}
