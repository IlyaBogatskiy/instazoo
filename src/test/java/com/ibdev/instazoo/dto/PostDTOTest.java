package com.ibdev.instazoo.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

class PostDTOTest {

    @Test
    void testConstructor() {
        PostDTO actualPostDTO = new PostDTO();
        actualPostDTO.setDescription("The characteristics of someone or something");
        actualPostDTO.setId(1L);
        actualPostDTO.setLikes(1);
        actualPostDTO.setLocation("Location");
        actualPostDTO.setTitle("Dr");
        actualPostDTO.setUsername("janedoe");
        HashSet<String> stringSet = new HashSet<>();
        actualPostDTO.setUsersLiked(stringSet);
        assertEquals("The characteristics of someone or something", actualPostDTO.getDescription());
        assertEquals(1L, actualPostDTO.getId().longValue());
        assertEquals(1, actualPostDTO.getLikes().intValue());
        assertEquals("Location", actualPostDTO.getLocation());
        assertEquals("Dr", actualPostDTO.getTitle());
        assertEquals("janedoe", actualPostDTO.getUsername());
        assertSame(stringSet, actualPostDTO.getUsersLiked());
    }
}

