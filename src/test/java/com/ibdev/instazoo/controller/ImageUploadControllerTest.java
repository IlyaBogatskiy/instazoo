package com.ibdev.instazoo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.model.ImageModel;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.payload.response.MessageResponse;
import com.ibdev.instazoo.repository.ImageRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.impl.ImageUploadServiceImpl;
import com.ibdev.instazoo.service.interfaces.ImageUploadService;
import com.sun.security.auth.UserPrincipal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ImageUploadController.class})
@ExtendWith(SpringExtension.class)
class ImageUploadControllerTest {

    @Autowired
    private ImageUploadController imageUploadController;

    @MockBean
    private ImageUploadService imageUploadService;

    @Test
    void testUploadImageToUser() throws IOException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);

        ImageModel imageModel1 = new ImageModel();
        imageModel1.setId(1L);
        imageModel1.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel1.setName("Name");
        imageModel1.setPostId(1L);
        imageModel1.setUserId(1L);
        Optional<ImageModel> ofResult = Optional.of(imageModel1);
        ImageRepository imageRepository = mock(ImageRepository.class);
        doNothing().when(imageRepository).delete((ImageModel) any());
        when(imageRepository.save((ImageModel) any())).thenReturn(imageModel);
        when(imageRepository.findByUserId((Long) any())).thenReturn(ofResult);

        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setBio("Bio");
        user.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setLastname("Doe");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.of(user));
        ImageUploadController imageUploadController = new ImageUploadController(
                new ImageUploadServiceImpl(imageRepository, userRepository));
        MockMultipartFile multipartFile = new MockMultipartFile("Name",
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        ResponseEntity<MessageResponse> actualUploadImageToUserResult = imageUploadController
                .uploadImageToUser(multipartFile, new UserPrincipal("principal"));
        assertTrue(actualUploadImageToUserResult.hasBody());
        assertTrue(actualUploadImageToUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUploadImageToUserResult.getStatusCode());
        assertEquals("Image uploaded successfully!", actualUploadImageToUserResult.getBody().getMessage());
        verify(imageRepository).save((ImageModel) any());
        verify(imageRepository).findByUserId((Long) any());
        verify(imageRepository).delete((ImageModel) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUploadImageToUser2() throws UnsupportedEncodingException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);

        ImageModel imageModel1 = new ImageModel();
        imageModel1.setId(1L);
        imageModel1.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel1.setName("Name");
        imageModel1.setPostId(1L);
        imageModel1.setUserId(1L);
        Optional<ImageModel> ofResult = Optional.of(imageModel1);
        ImageRepository imageRepository = mock(ImageRepository.class);
        doNothing().when(imageRepository).delete((ImageModel) any());
        when(imageRepository.save((ImageModel) any())).thenReturn(imageModel);
        when(imageRepository.findByUserId((Long) any())).thenReturn(ofResult);

        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setBio("Bio");
        user.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setLastname("Doe");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.of(user));
        ImageUploadController imageUploadController = new ImageUploadController(
                new ImageUploadServiceImpl(imageRepository, userRepository));
        ResponseEntity<MessageResponse> actualUploadImageToUserResult = imageUploadController.uploadImageToUser(null,
                new UserPrincipal("principal"));
        assertTrue(actualUploadImageToUserResult.hasBody());
        assertTrue(actualUploadImageToUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUploadImageToUserResult.getStatusCode());
        assertEquals("Image uploaded successfully!", actualUploadImageToUserResult.getBody().getMessage());
        verify(imageRepository).findByUserId((Long) any());
        verify(imageRepository).delete((ImageModel) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUploadImageToPost() throws IOException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        ImageUploadService imageUploadService = mock(ImageUploadService.class);
        when(imageUploadService.uploadImageToPost((MultipartFile) any(), (Long) any(), (Principal) any()))
                .thenReturn(imageModel);
        ImageUploadController imageUploadController = new ImageUploadController(imageUploadService);
        MockMultipartFile multipartFile = new MockMultipartFile("Name",
                new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        ResponseEntity<MessageResponse> actualUploadImageToPostResult = imageUploadController.uploadImageToPost("42",
                multipartFile, new UserPrincipal("principal"));
        assertTrue(actualUploadImageToPostResult.hasBody());
        assertTrue(actualUploadImageToPostResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUploadImageToPostResult.getStatusCode());
        assertEquals("Image uploaded successfully!", actualUploadImageToPostResult.getBody().getMessage());
        verify(imageUploadService).uploadImageToPost((MultipartFile) any(), (Long) any(), (Principal) any());
    }

    @Test
    void testGetImageForUser() throws Exception {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        when(imageUploadService.getImageToUser((Principal) any())).thenReturn(imageModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/image/profileImage");
        MockMvcBuilders.standaloneSetup(imageUploadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Name\",\"imageBytes\":\"QVhBWEFYQVg=\",\"userId\":1,\"postId\":1}"));
    }

    @Test
    void testGetImageToPost() throws Exception {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        when(imageUploadService.getImageToPost((Long) any())).thenReturn(imageModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/image/{postId}/image", "42");
        MockMvcBuilders.standaloneSetup(imageUploadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Name\",\"imageBytes\":\"QVhBWEFYQVg=\",\"userId\":1,\"postId\":1}"));
    }
}

