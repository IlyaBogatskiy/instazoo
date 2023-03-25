package com.ibdev.instazoo.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import javax.validation.ClockProvider;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {

    @Test
    void testIsValid() {
        EmailValidator emailValidator = new EmailValidator();
        ClockProvider clockProvider = mock(ClockProvider.class);
        assertTrue(emailValidator.isValid("jane.doe@example.org",
                new ConstraintValidatorContextImpl(clockProvider, PathImpl.createRootPath(), null,
                        "Constraint Validator Payload", ExpressionLanguageFeatureLevel.DEFAULT,
                        ExpressionLanguageFeatureLevel.DEFAULT)));
    }

    @Test
    void testIsValid2() {
        EmailValidator emailValidator = new EmailValidator();
        ClockProvider clockProvider = mock(ClockProvider.class);
        assertFalse(emailValidator.isValid("foo",
                new ConstraintValidatorContextImpl(clockProvider, PathImpl.createRootPath(), null,
                        "Constraint Validator Payload", ExpressionLanguageFeatureLevel.DEFAULT,
                        ExpressionLanguageFeatureLevel.DEFAULT)));
    }
}

