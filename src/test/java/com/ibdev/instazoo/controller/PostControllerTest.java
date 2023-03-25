package com.ibdev.instazoo.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibdev.instazoo.dto.PostDTO;
import com.ibdev.instazoo.facade.PostFacade;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.service.interfaces.PostService;
import com.ibdev.instazoo.validations.ResponseErrorValidation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {PostController.class})
@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @Autowired
    private PostController postController;

    @MockBean
    private PostFacade postFacade;

    @MockBean
    private PostService postService;

    @MockBean
    private ResponseErrorValidation responseErrorValidation;

    @Test
    void testCreatePost() throws Exception {
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
        when(postService.createPost((PostDTO) any(), (Principal) any())).thenReturn(post);

        PostDTO postDTO = new PostDTO();
        postDTO.setDescription("The characteristics of someone or something");
        postDTO.setId(1L);
        postDTO.setLikes(1);
        postDTO.setLocation("Location");
        postDTO.setTitle("Dr");
        postDTO.setUsername("janedoe");
        postDTO.setUsersLiked(new HashSet<>());
        when(postFacade.postToPostDTO((Post) any())).thenReturn(postDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        PostDTO postDTO1 = new PostDTO();
        postDTO1.setDescription("The characteristics of someone or something");
        postDTO1.setId(1L);
        postDTO1.setLikes(1);
        postDTO1.setLocation("Location");
        postDTO1.setTitle("Dr");
        postDTO1.setUsername("janedoe");
        postDTO1.setUsersLiked(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(postDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(postController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testCreatePost2() throws Exception {
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
        when(postService.createPost((PostDTO) any(), (Principal) any())).thenReturn(post);

        PostDTO postDTO = new PostDTO();
        postDTO.setDescription("The characteristics of someone or something");
        postDTO.setId(1L);
        postDTO.setLikes(1);
        postDTO.setLocation("Location");
        postDTO.setTitle("Dr");
        postDTO.setUsername("janedoe");
        postDTO.setUsersLiked(new HashSet<>());
        when(postFacade.postToPostDTO((Post) any())).thenReturn(postDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);

        PostDTO postDTO1 = new PostDTO();
        postDTO1.setDescription("The characteristics of someone or something");
        postDTO1.setId(1L);
        postDTO1.setLikes(1);
        postDTO1.setLocation("Location");
        postDTO1.setTitle("Dr");
        postDTO1.setUsername("janedoe");
        postDTO1.setUsersLiked(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(postDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"location\":\"Location"
                                        + "\",\"username\":\"janedoe\",\"likes\":1,\"usersLiked\":[]}"));
    }

    @Test
    void testGetAllPosts() throws Exception {
        when(postService.getAllPosts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/post/all");
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllPosts2() throws Exception {
        when(postService.getAllPosts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/post/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllPostsForUser() throws Exception {
        when(postService.getAllPostsForUser((Principal) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/post/user/posts");
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllPostsForUser2() throws Exception {
        when(postService.getAllPostsForUser((Principal) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/post/user/posts");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testLikePost() throws Exception {
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
        when(postService.likePost((Long) any(), (String) any())).thenReturn(post);

        PostDTO postDTO = new PostDTO();
        postDTO.setDescription("The characteristics of someone or something");
        postDTO.setId(1L);
        postDTO.setLikes(1);
        postDTO.setLocation("Location");
        postDTO.setTitle("Dr");
        postDTO.setUsername("janedoe");
        postDTO.setUsersLiked(new HashSet<>());
        when(postFacade.postToPostDTO((Post) any())).thenReturn(postDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/post/{postId}/{username}/like",
                "42", "janedoe");
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"location\":\"Location"
                                        + "\",\"username\":\"janedoe\",\"likes\":1,\"usersLiked\":[]}"));
    }

    @Test
    void testDeletePost() throws Exception {
        doNothing().when(postService).deletePost((Long) any(), (Principal) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/post/{postId}/delete", "42");
        MockMvcBuilders.standaloneSetup(postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Post deleted successfully!\"}"));
    }
}

