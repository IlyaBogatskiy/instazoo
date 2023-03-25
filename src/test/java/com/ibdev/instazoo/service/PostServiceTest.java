package com.ibdev.instazoo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.dto.PostDTO;
import com.ibdev.instazoo.exceptions.PostNotFoundException;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.Comment;
import com.ibdev.instazoo.model.ImageModel;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.repository.ImageRepository;
import com.ibdev.instazoo.repository.PostRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.impl.PostServiceImpl;
import com.sun.security.auth.UserPrincipal;

import java.io.UnsupportedEncodingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PostServiceTest {

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreatePost() {
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
        when(postRepository.save((Post) any())).thenReturn(post);

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
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);

        PostDTO postDTO = new PostDTO();
        postDTO.setDescription("The characteristics of someone or something");
        postDTO.setId(1L);
        postDTO.setLikes(1);
        postDTO.setLocation("Location");
        postDTO.setTitle("Dr");
        postDTO.setUsername("janedoe");
        postDTO.setUsersLiked(new HashSet<>());
        assertSame(post, postService.createPost(postDTO, new UserPrincipal("principal")));
        verify(postRepository).save((Post) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testCreatePost2() {
        when(postRepository.save((Post) any())).thenThrow(new UserExistException("An error occurred"));

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

        PostDTO postDTO = new PostDTO();
        postDTO.setDescription("The characteristics of someone or something");
        postDTO.setId(1L);
        postDTO.setLikes(1);
        postDTO.setLocation("Location");
        postDTO.setTitle("Dr");
        postDTO.setUsername("janedoe");
        postDTO.setUsersLiked(new HashSet<>());
        assertThrows(UserExistException.class, () -> postService.createPost(postDTO, new UserPrincipal("principal")));
        verify(postRepository).save((Post) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testCreatePost3() {
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
        when(postRepository.save((Post) any())).thenReturn(post);
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());

        PostDTO postDTO = new PostDTO();
        postDTO.setDescription("The characteristics of someone or something");
        postDTO.setId(1L);
        postDTO.setLikes(1);
        postDTO.setLocation("Location");
        postDTO.setTitle("Dr");
        postDTO.setUsername("janedoe");
        postDTO.setUsersLiked(new HashSet<>());
        assertThrows(UserExistException.class, () -> postService.createPost(postDTO, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetAllPosts() {
        ArrayList<Post> postList = new ArrayList<>();
        when(postRepository.findAllByOrderByCreatedDateDesc()).thenReturn(postList);
        List<Post> actualAllPosts = postService.getAllPosts();
        assertSame(postList, actualAllPosts);
        assertTrue(actualAllPosts.isEmpty());
        verify(postRepository).findAllByOrderByCreatedDateDesc();
    }

    @Test
    void testGetAllPosts2() {
        when(postRepository.findAllByOrderByCreatedDateDesc()).thenThrow(new UserExistException("An error occurred"));
        assertThrows(UserExistException.class, () -> postService.getAllPosts());
        verify(postRepository).findAllByOrderByCreatedDateDesc();
    }

    @Test
    void testGetPostById() {
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
        when(postRepository.findFindPostByIdAndUser((Long) any(), (User) any())).thenReturn(ofResult);

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
        assertSame(post, postService.getPostById(1L, new UserPrincipal("principal")));
        verify(postRepository).findFindPostByIdAndUser((Long) any(), (User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetPostById2() {
        when(postRepository.findFindPostByIdAndUser((Long) any(), (User) any()))
                .thenThrow(new UserExistException("An error occurred"));

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
        assertThrows(UserExistException.class, () -> postService.getPostById(1L, new UserPrincipal("principal")));
        verify(postRepository).findFindPostByIdAndUser((Long) any(), (User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetPostById3() {
        when(postRepository.findFindPostByIdAndUser((Long) any(), (User) any())).thenReturn(Optional.empty());

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
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1L, new UserPrincipal("principal")));
        verify(postRepository).findFindPostByIdAndUser((Long) any(), (User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetPostById4() {
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
        when(postRepository.findFindPostByIdAndUser((Long) any(), (User) any())).thenReturn(ofResult);
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UserExistException.class, () -> postService.getPostById(1L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetAllPostsForUser() {
        ArrayList<Post> postList = new ArrayList<>();
        when(postRepository.findAllByUserOrderByCreatedDateDesc((User) any())).thenReturn(postList);

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
        List<Post> actualAllPostsForUser = postService.getAllPostsForUser(new UserPrincipal("principal"));
        assertSame(postList, actualAllPostsForUser);
        assertTrue(actualAllPostsForUser.isEmpty());
        verify(postRepository).findAllByUserOrderByCreatedDateDesc((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetAllPostsForUser2() {
        when(postRepository.findAllByUserOrderByCreatedDateDesc((User) any()))
                .thenThrow(new UserExistException("An error occurred"));

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
        assertThrows(UserExistException.class, () -> postService.getAllPostsForUser(new UserPrincipal("principal")));
        verify(postRepository).findAllByUserOrderByCreatedDateDesc((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetAllPostsForUser3() {
        when(postRepository.findAllByUserOrderByCreatedDateDesc((User) any())).thenReturn(new ArrayList<>());
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UserExistException.class, () -> postService.getAllPostsForUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testLikePost() {
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
        when(postRepository.save((Post) any())).thenReturn(post1);
        when(postRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(post1, postService.likePost(1L, "janedoe"));
        verify(postRepository).save((Post) any());
        verify(postRepository).findById((Long) any());
    }

    @Test
    void testLikePost2() {
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
        when(postRepository.save((Post) any())).thenThrow(new UserExistException("An error occurred"));
        when(postRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(UserExistException.class, () -> postService.likePost(1L, "janedoe"));
        verify(postRepository).save((Post) any());
        verify(postRepository).findById((Long) any());
    }

    @Test
    void testDeletePost() throws UnsupportedEncodingException {
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
        doNothing().when(postRepository).delete((Post) any());
        when(postRepository.findFindPostByIdAndUser((Long) any(), (User) any())).thenReturn(ofResult);

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

        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        Optional<ImageModel> ofResult2 = Optional.of(imageModel);
        doNothing().when(imageRepository).delete((ImageModel) any());
        when(imageRepository.findByPostId((Long) any())).thenReturn(ofResult2);
        postService.deletePost(1L, new UserPrincipal("principal"));
        verify(postRepository).findFindPostByIdAndUser((Long) any(), (User) any());
        verify(postRepository).delete((Post) any());
        verify(userRepository).findUserByUsername((String) any());
        verify(imageRepository).findByPostId((Long) any());
        verify(imageRepository).delete((ImageModel) any());
    }

    @Test
    void testDeletePost2() throws UnsupportedEncodingException {
        doNothing().when(postRepository).delete((Post) any());
        when(postRepository.findFindPostByIdAndUser((Long) any(), (User) any())).thenReturn(Optional.empty());

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
        when(post.getUser()).thenReturn(user1);
        when(post.getId()).thenReturn(1L);
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
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);

        ImageModel imageModel = new ImageModel();
        imageModel.setId(1L);
        imageModel.setImageBytes("AXAXAXAX".getBytes("UTF-8"));
        imageModel.setName("Name");
        imageModel.setPostId(1L);
        imageModel.setUserId(1L);
        Optional<ImageModel> ofResult1 = Optional.of(imageModel);
        doNothing().when(imageRepository).delete((ImageModel) any());
        when(imageRepository.findByPostId((Long) any())).thenReturn(ofResult1);
        assertThrows(PostNotFoundException.class, () -> postService.deletePost(1L, new UserPrincipal("principal")));
        verify(postRepository).findFindPostByIdAndUser((Long) any(), (User) any());
        verify(post).setComments((List<Comment>) any());
        verify(post).setCreatedDate((LocalDateTime) any());
        verify(post).setDescription((String) any());
        verify(post).setId((Long) any());
        verify(post).setLikedUsers((Set<String>) any());
        verify(post).setLikes((Integer) any());
        verify(post).setLocation((String) any());
        verify(post).setTitle((String) any());
        verify(post).setUser((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }
}

