package com.example.feedbacktoolbackend.util.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentRoleValidator.class)
public @interface AuthorizedStudent {
    String message() default "No access rights";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
