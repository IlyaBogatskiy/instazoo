package com.ibdev.instazoo.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserDTOTest {

    @Test
    void testConstructor() {
        UserDTO actualUserDTO = new UserDTO();
        actualUserDTO.setBio("Bio");
        actualUserDTO.setFirstname("Jane");
        actualUserDTO.setId(1L);
        actualUserDTO.setLastname("Doe");
        actualUserDTO.setUsername("janedoe");
        assertEquals("Bio", actualUserDTO.getBio());
        assertEquals("Jane", actualUserDTO.getFirstname());
        assertEquals(1L, actualUserDTO.getId().longValue());
        assertEquals("Doe", actualUserDTO.getLastname());
        assertEquals("janedoe", actualUserDTO.getUsername());
    }
}

