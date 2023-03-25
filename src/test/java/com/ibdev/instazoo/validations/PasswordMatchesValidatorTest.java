package com.ibdev.instazoo.validations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.ibdev.instazoo.payload.request.SignUpRequest;

import javax.validation.ClockProvider;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.junit.jupiter.api.Test;

class PasswordMatchesValidatorTest {

    @Test
    void testIsValid() {
        PasswordMatchesValidator passwordMatchesValidator = new PasswordMatchesValidator();

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setConfirmPassword("iloveyou");
        signUpRequest.setEmail("jane.doe@example.org");
        signUpRequest.setFirstname("Jane");
        signUpRequest.setLastname("Doe");
        signUpRequest.setPassword("iloveyou");
        signUpRequest.setUsername("janedoe");
        ClockProvider clockProvider = mock(ClockProvider.class);
        assertTrue(passwordMatchesValidator.isValid(signUpRequest,
                new ConstraintValidatorContextImpl(clockProvider, PathImpl.createRootPath(), null,
                        "Constraint Validator Payload", ExpressionLanguageFeatureLevel.DEFAULT,
                        ExpressionLanguageFeatureLevel.DEFAULT)));
    }
}

