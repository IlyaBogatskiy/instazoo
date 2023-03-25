package com.ibdev.instazoo.security.component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.model.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JWTTokenProvider.class})
@ExtendWith(SpringExtension.class)
class JWTTokenProviderTest {

    @Autowired
    private JWTTokenProvider jWTTokenProvider;

    @Test
    void testGenerateToken() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getLastname()).thenReturn("Doe");
        when(user.getName()).thenReturn("Name");
        jWTTokenProvider.generateToken(new TestingAuthenticationToken(user, "Credentials"));
        verify(user).getId();
        verify(user).getEmail();
        verify(user).getLastname();
        verify(user).getName();
    }

    @Test
    void testValidateToken() {
        assertFalse(jWTTokenProvider.validateToken("ABC123"));
        assertFalse(jWTTokenProvider.validateToken("com.ibdev.instazoo.model.User"));
        assertFalse(jWTTokenProvider.validateToken(""));
    }
}

