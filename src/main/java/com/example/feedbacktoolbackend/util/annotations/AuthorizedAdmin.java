package com.example.feedbacktoolbackend.util.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdminRoleValidator.class)
public @interface AuthorizedAdmin {
    String message() default "No access rights";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
