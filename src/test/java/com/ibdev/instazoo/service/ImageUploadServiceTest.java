package com.ibdev.instazoo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.ImageModel;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.repository.ImageRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.impl.ImageUploadServiceImpl;
import com.sun.security.auth.UserPrincipal;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ImageUploadServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ImageUploadServiceTest {

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageUploadServiceImpl imageUploadService;

    @MockBean
    private UserRepository userRepository;

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
        Optional<User> ofResult1 = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        assertSame(imageModel, imageUploadService.uploadImageToUser(file, new UserPrincipal("principal")));
        verify(imageRepository).save((ImageModel) any());
        verify(imageRepository).findByUserId((Long) any());
        verify(imageRepository).delete((ImageModel) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUploadImageToUser2() throws IOException {
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
        doThrow(new UserExistException("An error occurred")).when(imageRepository).delete((ImageModel) any());
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
        Optional<User> ofResult1 = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        assertThrows(UserExistException.class,
                () -> imageUploadService.uploadImageToUser(file, new UserPrincipal("principal")));
        verify(imageRepository).findByUserId((Long) any());
        verify(imageRepository).delete((ImageModel) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUploadImageToUser3() throws IOException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        doNothing().when(imageRepository).delete((ImageModel) any());
        when(imageRepository.save((ImageModel) any())).thenReturn(imageModel);
        when(imageRepository.findByUserId((Long) any())).thenReturn(Optional.empty());

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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        assertSame(imageModel, imageUploadService.uploadImageToUser(file, new UserPrincipal("principal")));
        verify(imageRepository).save((ImageModel) any());
        verify(imageRepository).findByUserId((Long) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUploadImageToPost() throws IOException {
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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        assertThrows(IllegalStateException.class,
                () -> imageUploadService.uploadImageToPost(file, 1L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUploadImageToPost2() throws IOException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        when(imageRepository.save((ImageModel) any())).thenReturn(imageModel);

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

        Post post = new Post();
        post.setComments(new ArrayList<>());
        post.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        post.setDescription("The characteristics of someone or something");
        post.setId(1L);
        post.setLikedUsers(new HashSet<>());
        post.setLikes(1);
        post.setLocation("Location");
        post.setTitle("Dr");
        post.setUser(user);

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(post);

        User user1 = new User();
        user1.setAuthorities(new ArrayList<>());
        user1.setBio("Bio");
        user1.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setLastname("Doe");
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPosts(postList);
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        MockMultipartFile file = new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

        assertSame(imageModel, imageUploadService.uploadImageToPost(file, 1L, new UserPrincipal("principal")));
        verify(imageRepository).save((ImageModel) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetImageToUser() throws UnsupportedEncodingException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        Optional<ImageModel> ofResult = Optional.of(imageModel);
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
        Optional<User> ofResult1 = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);
        ImageModel actualImageToUser = imageUploadService.getImageToUser(new UserPrincipal("principal"));
        assertSame(imageModel, actualImageToUser);
        assertEquals(0, actualImageToUser.getImageBytes().length);
        verify(imageRepository).findByUserId((Long) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetImageToUser2() {
        when(imageRepository.findByUserId((Long) any())).thenThrow(new UserExistException("An error occurred"));

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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        assertThrows(UserExistException.class, () -> imageUploadService.getImageToUser(new UserPrincipal("principal")));
        verify(imageRepository).findByUserId((Long) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetImageToPost() throws UnsupportedEncodingException {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        Optional<ImageModel> ofResult = Optional.of(imageModel);
        when(imageRepository.findByPostId((Long) any())).thenReturn(ofResult);
        ImageModel actualImageToPost = imageUploadService.getImageToPost(1L);
        assertSame(imageModel, actualImageToPost);
        assertEquals(0, actualImageToPost.getImageBytes().length);
        verify(imageRepository).findByPostId((Long) any());
    }
}

