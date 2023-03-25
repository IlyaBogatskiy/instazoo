package com.ibdev.instazoo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.dto.CommentDTO;
import com.ibdev.instazoo.exceptions.PostNotFoundException;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.Comment;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.repository.CommentRepository;
import com.ibdev.instazoo.repository.PostRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.impl.CommentServiceImpl;
import com.sun.security.auth.UserPrincipal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceTest {

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testSaveComment() {
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
        when(commentRepository.save((Comment) any())).thenReturn(comment);

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

        Post post1 = new Post();
        post1.setComments(new ArrayList<>());
        post1.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        post1.setDescription("The characteristics of someone or something");
        post1.setId(1L);
        post1.setLikedUsers(new HashSet<>());
        post1.setLikes(1);
        post1.setLocation("Location");
        post1.setTitle("Dr");
        post1.setUser(user1);
        Optional<Post> ofResult = Optional.of(post1);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setAuthorities(new ArrayList<>());
        user2.setBio("Bio");
        user2.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setLastname("Doe");
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setPosts(new ArrayList<>());
        user2.setRoles(new HashSet<>());
        user2.setUsername("janedoe");
        Optional<User> ofResult1 = Optional.of(user2);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMessage("Not all who wander are lost");
        commentDTO.setUsername("janedoe");
        assertSame(comment, commentService.saveComment(1L, commentDTO, new UserPrincipal("principal")));
        verify(commentRepository).save((Comment) any());
        verify(postRepository).findById((Long) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testSaveComment2() {
        when(commentRepository.save((Comment) any())).thenThrow(new UserExistException("An error occurred"));

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
        Optional<Post> ofResult = Optional.of(post);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);

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
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMessage("Not all who wander are lost");
        commentDTO.setUsername("janedoe");
        assertThrows(UserExistException.class,
                () -> commentService.saveComment(1L, commentDTO, new UserPrincipal("principal")));
        verify(commentRepository).save((Comment) any());
        verify(postRepository).findById((Long) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetAllCommentsForPost() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(commentRepository.findAllByPost((Post) any())).thenReturn(commentList);

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
        Optional<Post> ofResult = Optional.of(post);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);
        List<Comment> actualAllCommentsForPost = commentService.getAllCommentsForPost(1L);
        assertSame(commentList, actualAllCommentsForPost);
        assertTrue(actualAllCommentsForPost.isEmpty());
        verify(commentRepository).findAllByPost((Post) any());
        verify(postRepository).findById((Long) any());
    }

    @Test
    void testGetAllCommentsForPost2() {
        when(commentRepository.findAllByPost((Post) any())).thenThrow(new UserExistException("An error occurred"));

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
        Optional<Post> ofResult = Optional.of(post);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(UserExistException.class, () -> commentService.getAllCommentsForPost(1L));
        verify(commentRepository).findAllByPost((Post) any());
        verify(postRepository).findById((Long) any());
    }

    @Test
    void testGetAllCommentsForPost3() {
        when(commentRepository.findAllByPost((Post) any())).thenReturn(new ArrayList<>());
        when(postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> commentService.getAllCommentsForPost(1L));
        verify(postRepository).findById((Long) any());
    }

    @Test
    void testDeleteComment() {
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
        Optional<Comment> ofResult = Optional.of(comment);
        doNothing().when(commentRepository).delete((Comment) any());
        when(commentRepository.findById((Long) any())).thenReturn(ofResult);
        commentService.deleteComment(1L);
        verify(commentRepository).findById((Long) any());
        verify(commentRepository).delete((Comment) any());
    }
}

