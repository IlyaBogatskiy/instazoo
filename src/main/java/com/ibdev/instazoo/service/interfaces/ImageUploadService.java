package com.ibdev.instazoo.service.interfaces;

import com.ibdev.instazoo.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ImageUploadService {

    ImageModel uploadImageToUser(MultipartFile file, Principal principal);

    ImageModel uploadImageToPost(MultipartFile file, Long id, Principal principal);

    ImageModel getImageToUser(Principal principal);

    ImageModel getImageToPost(Long id);
}
