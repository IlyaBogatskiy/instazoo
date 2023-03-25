package com.ibdev.instazoo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

class ImageModelTest {

    @Test
    void testConstructor() throws UnsupportedEncodingException {
        ImageModel actualImageModel = new ImageModel();
        actualImageModel.setId(1L);
        actualImageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        actualImageModel.setName("Name");
        actualImageModel.setPostId(1L);
        actualImageModel.setUserId(1L);
        assertEquals(1L, actualImageModel.getId().longValue());
        assertEquals("Name", actualImageModel.getName());
        assertEquals(1L, actualImageModel.getPostId().longValue());
        assertEquals(1L, actualImageModel.getUserId().longValue());
    }
}

