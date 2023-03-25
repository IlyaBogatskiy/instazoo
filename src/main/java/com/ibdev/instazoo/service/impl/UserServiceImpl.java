package com.ibdev.instazoo.service.impl;

import com.ibdev.instazoo.dto.UserDTO;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.model.enums.Role;
import com.ibdev.instazoo.payload.request.SignUpRequest;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.interfaces.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(SignUpRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Role.USER);

        try {
            log.info("Saving user: {}",userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        log.info("Update user: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserExistException("Username not found with username: " + username));
    }
}
