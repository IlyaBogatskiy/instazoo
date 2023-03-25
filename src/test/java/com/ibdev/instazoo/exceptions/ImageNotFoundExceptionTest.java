package com.ibdev.instazoo.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ImageNotFoundExceptionTest {

    @Test
    void testConstructor() {
        ImageNotFoundException actualImageNotFoundException = new ImageNotFoundException("An error occurred");
        assertNull(actualImageNotFoundException.getCause());
        assertEquals(0, actualImageNotFoundException.getSuppressed().length);
        assertEquals("An error occurred", actualImageNotFoundException.getMessage());
        assertEquals("An error occurred", actualImageNotFoundException.getLocalizedMessage());
    }
}

