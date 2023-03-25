package com.ibdev.instazoo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.dto.PostDTO;
import com.ibdev.instazoo.model.Comment;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostFacade.class})
@ExtendWith(SpringExtension.class)
class PostFacadeTest {

    @Autowired
    private PostFacade postFacade;

    @Test
    void testPostToPostDTO() {
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
        PostDTO actualPostToPostDTOResult = postFacade.postToPostDTO(post);
        assertEquals("The characteristics of someone or something", actualPostToPostDTOResult.getDescription());
        assertTrue(actualPostToPostDTOResult.getUsersLiked().isEmpty());
        assertEquals("janedoe", actualPostToPostDTOResult.getUsername());
        assertEquals("Dr", actualPostToPostDTOResult.getTitle());
        assertEquals("Location", actualPostToPostDTOResult.getLocation());
        assertEquals(1, actualPostToPostDTOResult.getLikes().intValue());
        assertEquals(1L, actualPostToPostDTOResult.getId().longValue());
    }

    @Test
    void testPostToPostDTO2() {
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

        User user1 = new User();
        user1.setAuthorities(new ArrayList<>());
        user1.setBio("Bio");
        user1.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setLastname("Doe");
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setPosts(new ArrayList<>());
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");
        Post post = mock(Post.class);
        when(post.getLikes()).thenReturn(1);
        when(post.getId()).thenReturn(1L);
        when(post.getDescription()).thenReturn("The characteristics of someone or something");
        when(post.getLocation()).thenReturn("Location");
        when(post.getTitle()).thenReturn("Dr");
        when(post.getLikedUsers()).thenReturn(new HashSet<>());
        when(post.getUser()).thenReturn(user1);
        doNothing().when(post).setComments((List<Comment>) any());
        doNothing().when(post).setCreatedDate((LocalDateTime) any());
        doNothing().when(post).setDescription((String) any());
        doNothing().when(post).setId((Long) any());
        doNothing().when(post).setLikedUsers((Set<String>) any());
        doNothing().when(post).setLikes((Integer) any());
        doNothing().when(post).setLocation((String) any());
        doNothing().when(post).setTitle((String) any());
        doNothing().when(post).setUser((User) any());
        post.setComments(new ArrayList<>());
        post.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        post.setDescription("The characteristics of someone or something");
        post.setId(1L);
        post.setLikedUsers(new HashSet<>());
        post.setLikes(1);
        post.setLocation("Location");
        post.setTitle("Dr");
        post.setUser(user);
        PostDTO actualPostToPostDTOResult = postFacade.postToPostDTO(post);
        assertEquals("The characteristics of someone or something", actualPostToPostDTOResult.getDescription());
        assertTrue(actualPostToPostDTOResult.getUsersLiked().isEmpty());
        assertEquals("janedoe", actualPostToPostDTOResult.getUsername());
        assertEquals("Dr", actualPostToPostDTOResult.getTitle());
        assertEquals("Location", actualPostToPostDTOResult.getLocation());
        assertEquals(1, actualPostToPostDTOResult.getLikes().intValue());
        assertEquals(1L, actualPostToPostDTOResult.getId().longValue());
        verify(post).getUser();
        verify(post).getLikes();
        verify(post).getId();
        verify(post).getDescription();
        verify(post).getLocation();
        verify(post).getTitle();
        verify(post).getLikedUsers();
        verify(post).setComments((List<Comment>) any());
        verify(post).setCreatedDate((LocalDateTime) any());
        verify(post).setDescription((String) any());
        verify(post).setId((Long) any());
        verify(post).setLikedUsers((Set<String>) any());
        verify(post).setLikes((Integer) any());
        verify(post).setLocation((String) any());
        verify(post).setTitle((String) any());
        verify(post).setUser((User) any());
    }
}

