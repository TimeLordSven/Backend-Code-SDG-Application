package com.example.feedbacktoolbackend.util.annotations;


import com.example.feedbacktoolbackend.enums.Role;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public abstract class BaseRoleValidator<T extends Annotation> implements ConstraintValidator<T, UserBusiness> {
    protected Role expectedRole;

    /**
     * Calls the abstract initializeRole method before validating
     *
     * @param annotation annotation instance for a given constraint declaration
     * @author Anish Raghoenath
     */
    @Override
    public void initialize(T annotation) {
        this.initializeRole();
    }

    abstract public void initializeRole();

    /**
     * Validates the targets role
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return true if it has the correct role
     * @author Anish Raghoenath
     */
    @Override
    public boolean isValid(UserBusiness value, ConstraintValidatorContext context) {
        return value.hasRole(expectedRole);
    }
}
