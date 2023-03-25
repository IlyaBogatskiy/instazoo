package com.ibdev.instazoo.model;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void testOnCreate() {
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
        Post post = mock(Post.class);
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

        Comment comment = new Comment();
        comment.setPost(post);
        comment.onCreate();
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

