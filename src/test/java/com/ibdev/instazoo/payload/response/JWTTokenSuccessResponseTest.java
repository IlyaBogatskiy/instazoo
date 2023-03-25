package com.ibdev.instazoo.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JWTTokenSuccessResponseTest {

    @Test
    void testConstructor() {
        JWTTokenSuccessResponse actualJwtTokenSuccessResponse = new JWTTokenSuccessResponse(true, "ABC123");
        actualJwtTokenSuccessResponse.setSuccess(true);
        actualJwtTokenSuccessResponse.setToken("ABC123");
        assertEquals("ABC123", actualJwtTokenSuccessResponse.getToken());
        assertTrue(actualJwtTokenSuccessResponse.isSuccess());
    }
}

