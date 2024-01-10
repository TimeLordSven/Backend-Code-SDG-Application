package com.example.feedbacktoolbackend.util.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FutureConstraintValidator implements ConstraintValidator<FutureConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        final String DATE_FORMAT = "dd-MM-yyyy";
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(DATE_FORMAT)).isAfter(LocalDate.now());
    }
}
