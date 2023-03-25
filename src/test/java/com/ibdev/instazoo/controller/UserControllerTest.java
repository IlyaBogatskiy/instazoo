package com.ibdev.instazoo.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibdev.instazoo.dto.UserDTO;
import com.ibdev.instazoo.facade.UserFacade;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.service.interfaces.UserService;
import com.ibdev.instazoo.validations.ResponseErrorValidation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @MockBean
    private ResponseErrorValidation responseErrorValidation;

    @Autowired
    private UserController userController;

    @MockBean
    private UserFacade userFacade;

    @MockBean
    private UserService userService;

    @Test
    void testGetCurrentUser() throws Exception {
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
        when(userService.getCurrentUser((Principal) any())).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"username\":\"janedoe\",\"bio\":\"Bio\"}"));
    }

    @Test
    void testUpdateUser() throws Exception {
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
        when(userService.updateUser((UserDTO) any(), (Principal) any())).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setBio("Bio");
        userDTO1.setFirstname("Jane");
        userDTO1.setId(1L);
        userDTO1.setLastname("Doe");
        userDTO1.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testUpdateUser2() throws Exception {
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
        when(userService.updateUser((UserDTO) any(), (Principal) any())).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setBio("Bio");
        userDTO1.setFirstname("Jane");
        userDTO1.setId(1L);
        userDTO1.setLastname("Doe");
        userDTO1.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"username\":\"janedoe\",\"bio\":\"Bio\"}"));
    }

    @Test
    void testUpdateUser3() throws Exception {
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
        when(userService.updateUser((UserDTO) any(), (Principal) any())).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setBio("Bio");
        userDTO1.setFirstname("");
        userDTO1.setId(1L);
        userDTO1.setLastname("Doe");
        userDTO1.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetUserProfile() throws Exception {
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
        when(userService.getUserById((Long) any())).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setBio("Bio");
        userDTO.setFirstname("Jane");
        userDTO.setId(1L);
        userDTO.setLastname("Doe");
        userDTO.setUsername("janedoe");
        when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/{userId}", "42");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"username\":\"janedoe\",\"bio\":\"Bio\"}"));
    }
}

