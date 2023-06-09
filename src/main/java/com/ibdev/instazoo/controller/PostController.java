package com.ibdev.instazoo.controller;

import com.ibdev.instazoo.dto.PostDTO;
import com.ibdev.instazoo.facade.PostFacade;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.payload.response.MessageResponse;
import com.ibdev.instazoo.service.interfaces.PostService;
import com.ibdev.instazoo.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostFacade postFacade;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public PostController(PostService postService,
                          PostFacade postFacade,
                          ResponseErrorValidation responseErrorValidation) {
        this.postService = postService;
        this.postFacade = postFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Post post = postService.createPost(postDTO, principal);
        PostDTO createdPostDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(createdPostDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOList = postService.getAllPosts()
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) {
        List<PostDTO> postDTOList = postService.getAllPostsForUser(principal)
              .stream()
              .map(postFacade::postToPostDTO)
              .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId,
                                            @PathVariable("username") String username) {
        Post post = postService.likePost(Long.parseLong(postId), username);
        PostDTO postDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId,
                                                      Principal principal) {
        postService.deletePost(Long.parseLong(postId), principal);

        return new ResponseEntity<>(new MessageResponse("Post deleted successfully!"), HttpStatus.OK);
    }
}
