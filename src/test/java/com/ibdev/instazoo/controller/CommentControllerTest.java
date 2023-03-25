package com.ibdev.instazoo.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibdev.instazoo.dto.CommentDTO;
import com.ibdev.instazoo.facade.CommentFacade;
import com.ibdev.instazoo.model.Comment;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.service.interfaces.CommentService;
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

@ContextConfiguration(classes = {CommentController.class})
@ExtendWith(SpringExtension.class)
class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @MockBean
    private CommentFacade commentFacade;

    @MockBean
    private CommentService commentService;

    @MockBean
    private ResponseErrorValidation responseErrorValidation;

    @Test
    void testCreateComment() throws Exception {
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

        Comment comment = new Comment();
        comment.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setMessage("Not all who wander are lost");
        comment.setPost(post);
        comment.setUserId(1L);
        comment.setUsername("janedoe");
        when(commentService.saveComment((Long) any(), (CommentDTO) any(), (Principal) any())).thenReturn(comment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMessage("Not all who wander are lost");
        commentDTO.setUsername("janedoe");
        when(commentFacade.commentToCommentDTO((Comment) any())).thenReturn(commentDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setId(1L);
        commentDTO1.setMessage("Not all who wander are lost");
        commentDTO1.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(commentDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/comment/{postId}/create", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(commentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeleteComment() throws Exception {
        doNothing().when(commentService).deleteComment((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/comment/{commentId}/delete",
                "42");
        MockMvcBuilders.standaloneSetup(commentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Post was deleted!\"}"));
    }

    @Test
    void testCreateComment2() throws Exception {
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

        Comment comment = new Comment();
        comment.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setMessage("Not all who wander are lost");
        comment.setPost(post);
        comment.setUserId(1L);
        comment.setUsername("janedoe");
        when(commentService.saveComment((Long) any(), (CommentDTO) any(), (Principal) any())).thenReturn(comment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMessage("Not all who wander are lost");
        commentDTO.setUsername("janedoe");
        when(commentFacade.commentToCommentDTO((Comment) any())).thenReturn(commentDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);

        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setId(1L);
        commentDTO1.setMessage("Not all who wander are lost");
        commentDTO1.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(commentDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/comment/{postId}/create", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(commentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"message\":\"Not all who wander are lost\",\"username\":\"janedoe\"}"));
    }

    @Test
    void testGetAllCommentsToPost() throws Exception {
        when(commentService.getAllCommentsForPost((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/comment/{postId}/all", "42");
        MockMvcBuilders.standaloneSetup(commentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

