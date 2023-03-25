package com.ibdev.instazoo.security.component;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JWTAuthenticationEntryPoint.class})
@ExtendWith(SpringExtension.class)
class JWTAuthenticationEntryPointTest {

    @Autowired
    private JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint;

    @Test
    void testCommence6() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = mock(Response.class);
        when(response.getWriter()).thenReturn(null);
        doNothing().when(response).setContentType((String) any());
        doNothing().when(response).setStatus(anyInt());
        jWTAuthenticationEntryPoint.commence(request, response, new AccountExpiredException("Msg"));
        verify(response).getWriter();
        verify(response).setContentType((String) any());
        verify(response).setStatus(anyInt());
    }
}

