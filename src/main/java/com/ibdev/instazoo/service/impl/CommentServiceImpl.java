package com.ibdev.instazoo.service.impl;

import com.ibdev.instazoo.dto.CommentDTO;
import com.ibdev.instazoo.exceptions.PostNotFoundException;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.Comment;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.repository.CommentRepository;
import com.ibdev.instazoo.repository.PostRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.interfaces.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment saveComment(Long id, CommentDTO commentDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(
                        "Post cannot be found for username: " + user.getUsername())
                );

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        log.info("Saving comment for post: {}", post.getId());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsForPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        return commentRepository.findAllByPost(post);
    }

    @Override
    public void deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);

        comment.ifPresent(commentRepository::delete);
        log.info("Removing comment: {}", comment.get().getId());
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserExistException("Username not found with username: " + username));
    }
}
