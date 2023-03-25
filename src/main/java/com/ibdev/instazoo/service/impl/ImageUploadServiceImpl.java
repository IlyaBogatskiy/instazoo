package com.ibdev.instazoo.service.impl;

import com.ibdev.instazoo.exceptions.ImageNotFoundException;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.ImageModel;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.repository.ImageRepository;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.interfaces.ImageUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Log4j2
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageUploadServiceImpl(ImageRepository imageRepository,
                                  UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ImageModel uploadImageToUser(MultipartFile file, Principal principal) {
        User user = getUserByPrincipal(principal);
        log.info("Upload image profile to user: {}", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        try {
            ImageModel imageModel = new ImageModel();
            imageModel.setUserId(user.getId());
            imageModel.setImageBytes(compressBytes(file.getBytes()));
            imageModel.setName(file.getOriginalFilename());

            return imageRepository.save(imageModel);
        } catch (Exception e) {
            log.error("Cannot upload image: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ImageModel uploadImageToPost(MultipartFile file, Long id, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = user.getPosts()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(toSinglePostCollector());

        try {
            ImageModel imageModel = new ImageModel();
            imageModel.setPostId(post.getId());
            imageModel.setImageBytes(file.getBytes());
            imageModel.setImageBytes(compressBytes(file.getBytes()));
            imageModel.setName(file.getOriginalFilename());
            log.info("Upload image profile to post: {}", post.getId());

            return imageRepository.save(imageModel);
        } catch (Exception e) {
            log.error("Cannot upload image: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ImageModel getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        ImageModel imageModel = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }

        return imageModel;
    }

    @Override
    public ImageModel getImageToPost(Long id) {
        ImageModel imageModel = imageRepository.findByPostId(id)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to post: " + id));

        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }

        return imageModel;
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (Exception e) {
            log.error("Cannot compress bytes: {}", e.getMessage());
        }

        log.info("Compressed image byte size: {}", outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {
            log.error("Cannot decompress bytes: {}", e.getMessage());
        }

        return outputStream.toByteArray();
    }

    private <T> Collector<T, ?, T> toSinglePostCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("Expected to find 1 post but found: " + list.size());
                    }
                    return list.get(0);
                }
        );
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserExistException("Username not found with username: " + username));
    }
}
