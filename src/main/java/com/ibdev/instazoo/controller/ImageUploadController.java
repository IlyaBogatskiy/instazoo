package com.ibdev.instazoo.controller;

import com.ibdev.instazoo.model.ImageModel;
import com.ibdev.instazoo.payload.response.MessageResponse;
import com.ibdev.instazoo.service.interfaces.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/image")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile multipartFile,
                                                             Principal principal) {
        imageUploadService.uploadImageToUser(multipartFile, principal);

        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully!"));
    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId,
                                                             @RequestParam("file") MultipartFile multipartFile,
                                                             Principal principal) {
        imageUploadService.uploadImageToPost(multipartFile, Long.parseLong(postId), principal);

        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully!"));
    }

    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel userImage = imageUploadService.getImageToUser(principal);

        return new  ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable("postId") String postId) {
        ImageModel postImage = imageUploadService.getImageToPost(Long.parseLong(postId));

        return new  ResponseEntity<>(postImage, HttpStatus.OK);
    }
}
