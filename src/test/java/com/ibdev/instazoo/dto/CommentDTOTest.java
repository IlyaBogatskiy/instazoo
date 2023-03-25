package com.ibdev.instazoo.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommentDTOTest {

    @Test
    void testConstructor() {
        CommentDTO actualCommentDTO = new CommentDTO();
        actualCommentDTO.setId(1L);
        actualCommentDTO.setMessage("Not all who wander are lost");
        actualCommentDTO.setUsername("janedoe");
        assertEquals(1L, actualCommentDTO.getId().longValue());
        assertEquals("Not all who wander are lost", actualCommentDTO.getMessage());
        assertEquals("janedoe", actualCommentDTO.getUsername());
    }
}

