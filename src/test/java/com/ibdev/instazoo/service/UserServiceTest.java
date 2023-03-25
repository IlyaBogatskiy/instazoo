package com.ibdev.instazoo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.dto.UserDTO;
import com.ibdev.instazoo.exceptions.UserExistException;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.payload.request.SignUpRequest;
import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.impl.UserServiceImpl;
import com.sun.security.auth.UserPrincipal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
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
        when(userRepository.save((User) any())).thenReturn(user);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setConfirmPassword("iloveyou");
        signUpRequest.setEmail("jane.doe@example.org");
        signUpRequest.setFirstname("Jane");
        signUpRequest.setLastname("Doe");
        signUpRequest.setPassword("iloveyou");
        signUpRequest.setUsername("janedoe");
        assertSame(user, userService.createUser(signUpRequest));
        verify(userRepository).save((User) any());
    }

    @Test
    void testUpdateUser() {
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
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        assertSame(user1, userService.updateUser(userDTO, new UserPrincipal("principal")));
        verify(userRepository).save((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUpdateUser2() {
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
        when(userRepository.save((User) any())).thenThrow(new UserExistException("An error occurred"));
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        assertThrows(UserExistException.class, () -> userService.updateUser(userDTO, new UserPrincipal("principal")));
        verify(userRepository).save((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testUpdateUser3() {
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
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        assertThrows(UserExistException.class, () -> userService.updateUser(userDTO, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetCurrentUser() {
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
        assertSame(user, userService.getCurrentUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetCurrentUser2() {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UserExistException.class, () -> userService.getCurrentUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    @Test
    void testGetCurrentUser4() {
        when(userRepository.findUserByUsername((String) any())).thenThrow(new UserExistException("An error occurred"));
        assertThrows(UserExistException.class, () -> userService.getCurrentUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }
}

