package com.ibdev.instazoo.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InvalidLoginResponseTest {

    @Test
    void testConstructor() {
        InvalidLoginResponse actualInvalidLoginResponse = new InvalidLoginResponse();
        assertEquals("Invalid Password!", actualInvalidLoginResponse.getPassword());
        assertEquals("Invalid Username!", actualInvalidLoginResponse.getUsername());
    }
}

