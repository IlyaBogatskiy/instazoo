package com.ibdev.instazoo.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class PostNotFoundExceptionTest {

    @Test
    void testConstructor() {
        PostNotFoundException actualPostNotFoundException = new PostNotFoundException("An error occurred");
        assertNull(actualPostNotFoundException.getCause());
        assertEquals(0, actualPostNotFoundException.getSuppressed().length);
        assertEquals("An error occurred", actualPostNotFoundException.getMessage());
        assertEquals("An error occurred", actualPostNotFoundException.getLocalizedMessage());
    }
}

