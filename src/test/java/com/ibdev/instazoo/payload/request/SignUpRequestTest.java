package com.ibdev.instazoo.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SignUpRequestTest {

    @Test
    void testConstructor() {
        SignUpRequest actualSignUpRequest = new SignUpRequest();
        actualSignUpRequest.setConfirmPassword("iloveyou");
        actualSignUpRequest.setEmail("jane.doe@example.org");
        actualSignUpRequest.setFirstname("Jane");
        actualSignUpRequest.setLastname("Doe");
        actualSignUpRequest.setPassword("iloveyou");
        actualSignUpRequest.setUsername("janedoe");
        assertEquals("iloveyou", actualSignUpRequest.getConfirmPassword());
        assertEquals("jane.doe@example.org", actualSignUpRequest.getEmail());
        assertEquals("Jane", actualSignUpRequest.getFirstname());
        assertEquals("Doe", actualSignUpRequest.getLastname());
        assertEquals("iloveyou", actualSignUpRequest.getPassword());
        assertEquals("janedoe", actualSignUpRequest.getUsername());
    }
}

