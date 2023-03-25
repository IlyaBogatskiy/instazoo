package com.ibdev.instazoo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testConstructor() {
        User actualUser = new User();
        assertNull(actualUser.getPassword());
        assertTrue(actualUser.isAccountNonExpired());
        assertTrue(actualUser.isAccountNonLocked());
        assertTrue(actualUser.isCredentialsNonExpired());
        assertTrue(actualUser.isEnabled());
    }

    @Test
    void testConstructor2() {
        User actualUser = new User(
                1L,
                "janedoe",
                "jane.doe@example.org",
                "iloveyou",
                new ArrayList<>()
        );

        assertEquals("iloveyou", actualUser.getPassword());
        assertTrue(actualUser.isAccountNonExpired());
        assertTrue(actualUser.isAccountNonLocked());
        assertTrue(actualUser.isCredentialsNonExpired());
        assertTrue(actualUser.isEnabled());
    }
}

