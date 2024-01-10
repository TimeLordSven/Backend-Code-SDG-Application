package com.example.feedbacktoolbackend.util.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureConstraintValidator.class)
public @interface FutureConstraint {
    String message() default "Date can't be in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}