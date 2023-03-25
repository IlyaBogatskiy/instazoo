package com.ibdev.instazoo.service.interfaces;

import com.ibdev.instazoo.dto.PostDTO;
import com.ibdev.instazoo.model.Post;

import java.security.Principal;
import java.util.List;

public interface PostService {

    Post createPost(PostDTO postDTO, Principal principal);

    List<Post> getAllPosts();

    Post getPostById(Long id, Principal principal);

    List<Post> getAllPostsForUser(Principal principal);

    Post likePost(Long id, String username);

    void deletePost(Long id, Principal principal);
}
