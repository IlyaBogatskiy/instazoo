package com.ibdev.instazoo.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

@ContextConfiguration(classes = {ResponseErrorValidation.class})
@ExtendWith(SpringExtension.class)
class ResponseErrorValidationTest {

    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @Test
    void testMapValidationService1() {
        assertNull(responseErrorValidation.mapValidationService(new BindException("Target", "Object Name")));
    }

    @Test
    void testMapValidationService2() {
        BeanPropertyBindingResult beanPropertyBindingResult = mock(BeanPropertyBindingResult.class);
        when(beanPropertyBindingResult.getAllErrors()).thenReturn(new ArrayList<>());
        when(beanPropertyBindingResult.getFieldErrors()).thenReturn(new ArrayList<>());
        when(beanPropertyBindingResult.hasErrors()).thenReturn(true);
        ResponseEntity<Object> actualMapValidationServiceResult = responseErrorValidation
                .mapValidationService(beanPropertyBindingResult);
        assertTrue(((Map<Object, Object>) actualMapValidationServiceResult.getBody()).isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualMapValidationServiceResult.getStatusCode());
        assertTrue(actualMapValidationServiceResult.getHeaders().isEmpty());
        verify(beanPropertyBindingResult).hasErrors();
        verify(beanPropertyBindingResult).getAllErrors();
        verify(beanPropertyBindingResult).getFieldErrors();
    }

    @Test
    void testMapValidationService3() {
        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        BeanPropertyBindingResult beanPropertyBindingResult = mock(BeanPropertyBindingResult.class);
        when(beanPropertyBindingResult.getAllErrors()).thenReturn(new ArrayList<>());
        when(beanPropertyBindingResult.getFieldErrors()).thenReturn(fieldErrorList);
        when(beanPropertyBindingResult.hasErrors()).thenReturn(true);
        ResponseEntity<Object> actualMapValidationServiceResult = responseErrorValidation
                .mapValidationService(beanPropertyBindingResult);
        assertEquals(1, ((Map<String, String>) actualMapValidationServiceResult.getBody()).size());
        assertTrue(actualMapValidationServiceResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualMapValidationServiceResult.getStatusCode());
        assertTrue(actualMapValidationServiceResult.getHeaders().isEmpty());
        verify(beanPropertyBindingResult).hasErrors();
        verify(beanPropertyBindingResult).getAllErrors();
        verify(beanPropertyBindingResult).getFieldErrors();
    }
}

