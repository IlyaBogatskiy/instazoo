package com.ibdev.instazoo.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class UserExistExceptionTest {

    @Test
    void testConstructor() {
        UserExistException actualUserExistException = new UserExistException("An error occurred");
        assertNull(actualUserExistException.getCause());
        assertEquals(0, actualUserExistException.getSuppressed().length);
        assertEquals("An error occurred", actualUserExistException.getMessage());
        assertEquals("An error occurred", actualUserExistException.getLocalizedMessage());
    }
}

