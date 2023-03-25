package com.ibdev.instazoo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.dto.CommentDTO;
import com.ibdev.instazoo.model.Comment;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommentFacade.class})
@ExtendWith(SpringExtension.class)
class CommentFacadeTest {

    @Autowired
    private CommentFacade commentFacade;

    @Test
    void testCommentToCommentDTO() {
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
        CommentDTO actualCommentToCommentDTOResult = commentFacade.commentToCommentDTO(comment);
        assertEquals(1L, actualCommentToCommentDTOResult.getId().longValue());
        assertEquals("janedoe", actualCommentToCommentDTOResult.getUsername());
        assertEquals("Not all who wander are lost", actualCommentToCommentDTOResult.getMessage());
    }

    @Test
    void testCommentToCommentDTO2() {
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
        Comment comment = mock(Comment.class);
        when(comment.getId()).thenReturn(1L);
        when(comment.getMessage()).thenReturn("Not all who wander are lost");
        when(comment.getUsername()).thenReturn("janedoe");
        doNothing().when(comment).setCreatedDate((LocalDateTime) any());
        doNothing().when(comment).setId((Long) any());
        doNothing().when(comment).setMessage((String) any());
        doNothing().when(comment).setPost((Post) any());
        doNothing().when(comment).setUserId((Long) any());
        doNothing().when(comment).setUsername((String) any());
        comment.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setMessage("Not all who wander are lost");
        comment.setPost(post);
        comment.setUserId(1L);
        comment.setUsername("janedoe");
        CommentDTO actualCommentToCommentDTOResult = commentFacade.commentToCommentDTO(comment);
        assertEquals(1L, actualCommentToCommentDTOResult.getId().longValue());
        assertEquals("janedoe", actualCommentToCommentDTOResult.getUsername());
        assertEquals("Not all who wander are lost", actualCommentToCommentDTOResult.getMessage());
        verify(comment).getId();
        verify(comment).getMessage();
        verify(comment).getUsername();
        verify(comment).setCreatedDate((LocalDateTime) any());
        verify(comment).setId((Long) any());
        verify(comment).setMessage((String) any());
        verify(comment).setPost((Post) any());
        verify(comment).setUserId((Long) any());
        verify(comment).setUsername((String) any());
    }
}

