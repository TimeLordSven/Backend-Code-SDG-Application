package com.example.feedbacktoolbackend.util.annotations;

import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
/**
 * BaseRoleValidator is an abstract class implementing ConstraintValidator for role-based validation.
 * Extend this class to create specific role validators.
 * @param <T> The annotation type for validation
 * @author Sven Molenaar
 */
public abstract class BaseRoleValidator<T extends Annotation> implements ConstraintValidator<T, UserBusiness> {

    protected Role expectedRole;

    @Override
    public void initialize(T annotation) {
        this.initializeRole();
    }

    abstract public void initializeRole();

    @Override
    public boolean isValid(UserBusiness value, ConstraintValidatorContext context) {
        return value.hasRole(expectedRole);
    }
}
