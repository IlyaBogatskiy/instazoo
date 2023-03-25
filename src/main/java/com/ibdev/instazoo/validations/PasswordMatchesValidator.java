package com.ibdev.instazoo.validations;

import com.ibdev.instazoo.annotations.PasswordMatches;
import com.ibdev.instazoo.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        SignUpRequest signUpRequest = (SignUpRequest) object;
        return signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword());
    }
}
