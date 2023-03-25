package com.ibdev.instazoo.service.impl;

import com.ibdev.instazoo.dto.PostDTO;
import com.ibdev.instazoo.exceptions.PostNotFoundException;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.ImageModel;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.repository.ImageRepository;
import com.ibdev.instazoo.repository.PostRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.interfaces.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setDescription(postDTO.getDescription());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        log.info("Saving post for user: {}", user.getEmail());
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Post getPostById(Long id, Principal principal) {
        User user = getUserByPrincipal(principal);

        return postRepository.findFindPostByIdAndUser(id, user)
              .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getEmail()));
    }

    @Override
    public List<Post> getAllPostsForUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    @Override
    public Post likePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(user -> user.equals(username))
                .findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1); // пытается убрать лайк
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        log.info("Liking post {} for user {}", post.getId(), post.getUser().getEmail());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id, Principal principal) {
        Post post = getPostById(id, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());

        log.info("Deleting post {} for user {}", post.getId(), post.getUser().getEmail());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserExistException("Username not found with username: " + username));
    }
}
