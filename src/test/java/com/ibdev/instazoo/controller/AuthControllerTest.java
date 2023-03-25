package com.ibdev.instazoo.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.payload.request.LoginRequest;
import com.ibdev.instazoo.payload.request.SignUpRequest;
import com.ibdev.instazoo.security.component.JWTTokenProvider;
import com.ibdev.instazoo.service.impl.UserServiceImpl;
import com.ibdev.instazoo.validations.ResponseErrorValidation;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTTokenProvider jWTTokenProvider;

    @MockBean
    private ResponseErrorValidation responseErrorValidation;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void testAuthenticateUser() throws Exception {
        when(jWTTokenProvider.generateToken((Authentication) any())).thenReturn("ABC123");
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("iloveyou");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testAuthenticateUser2() throws Exception {
        when(jWTTokenProvider.generateToken((Authentication) any())).thenReturn("ABC123");
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("iloveyou");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"success\":true,\"token\":\"ABC123\"}"));
    }

    @Test
    void testAuthenticateUser3() throws Exception {
        when(jWTTokenProvider.generateToken((Authentication) any())).thenReturn("ABC123");
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testRegisterUser() throws Exception {
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setConfirmPassword("iloveyou");
        signUpRequest.setEmail("jane.doe@example.org");
        signUpRequest.setFirstname("Jane");
        signUpRequest.setLastname("Doe");
        signUpRequest.setPassword("iloveyou");
        signUpRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testRegisterUser2() throws Exception {
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);

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
        when(userService.createUser((SignUpRequest) any())).thenReturn(user);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setConfirmPassword("iloveyou");
        signUpRequest.setEmail("jane.doe@example.org");
        signUpRequest.setFirstname("Jane");
        signUpRequest.setLastname("Doe");
        signUpRequest.setPassword("iloveyou");
        signUpRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"User registered successfully\"}"));
    }
}

