package com.example.feedbacktoolbackend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Represents a Data Transfer Object (DTO) for user information.
 * This record encapsulates user data, providing a concise and immutable representation.
 *
 * @author Sven Molenaar
 */
public record RegistrationDTO(
        @NotBlank(message = "First name cannot be null or an empty string")
        @Size(min = 1, max = 50, message = "First name should be between 1 and 50 characters long")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "First name can only contain alphabetic characters and cannot contain spaces")
        String firstName,

        @Pattern(regexp = "^[a-zA-Z]+$", message = "Prefix can only contain alphabetic characters")
        String prefixes,

        @NotBlank(message = "Last name cannot be null or an empty string")
        @Size(min = 1, max = 50, message = "Last name should be between 1 and 50 characters long")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name can only contain alphabetic characters and cannot contain spaces")
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
                message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password should have at least 8 characters")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}|:\"<>?,./;'\\[\\]`~]).*$", message = "Password should contain at least one special character")
        String password,

        @NotBlank(message = "Verify password cannot be blank")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}|:\"<>?,./;'\\[\\]`~]).*$", message = "Verify password should contain at least one special character")
        String verifyPassword
) {

}
