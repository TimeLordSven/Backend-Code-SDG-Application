package com.example.feedbacktoolbackend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

/**
 * DTO (Data Transfer Object) representing login credentials.
 *
 * @author Sven Molenaar
 */
public record LoginDTO(
        @Email
        @NotBlank
                (message = "Invalid email format") String email,
        @NotBlank(message = "Password cannot be blank") String password
) {
}