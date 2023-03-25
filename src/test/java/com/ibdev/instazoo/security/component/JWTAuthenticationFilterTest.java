package com.ibdev.instazoo.security.component;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.service.impl.CustomUserDetailsServiceImpl;

import java.io.IOException;

import java.nio.file.Paths;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

@ContextConfiguration(classes = {JWTAuthenticationFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class JWTAuthenticationFilterTest {

    @MockBean
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JWTAuthenticationFilter jWTAuthenticationFilter;

    @MockBean
    private JWTTokenProvider jWTTokenProvider;

    @Test
    void testDoFilterInternal() throws IOException, ServletException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jWTAuthenticationFilter.doFilterInternal(mockHttpServletRequest, response, filterChain);
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        assertFalse(mockHttpServletRequest.isRequestedSessionIdFromURL());
        assertTrue(mockHttpServletRequest.isRequestedSessionIdFromCookie());
        assertFalse(mockHttpServletRequest.isAsyncSupported());
        assertFalse(mockHttpServletRequest.isAsyncStarted());
        assertTrue(mockHttpServletRequest.isActive());
        assertTrue(mockHttpServletRequest.getSession() instanceof MockHttpSession);
        assertEquals("", mockHttpServletRequest.getServletPath());
        assertEquals(80, mockHttpServletRequest.getServerPort());
        assertEquals("localhost", mockHttpServletRequest.getServerName());
        assertEquals("http", mockHttpServletRequest.getScheme());
        assertEquals("", mockHttpServletRequest.getRequestURI());
        assertEquals(80, mockHttpServletRequest.getRemotePort());
        assertEquals("localhost", mockHttpServletRequest.getRemoteHost());
        assertEquals("HTTP/1.1", mockHttpServletRequest.getProtocol());
        assertEquals("", mockHttpServletRequest.getMethod());
        assertEquals(80, mockHttpServletRequest.getLocalPort());
        assertEquals("localhost", mockHttpServletRequest.getLocalName());
        assertTrue(mockHttpServletRequest.getInputStream() instanceof DelegatingServletInputStream);
        assertEquals(DispatcherType.REQUEST, mockHttpServletRequest.getDispatcherType());
        assertEquals("", mockHttpServletRequest.getContextPath());
        assertEquals(-1L, mockHttpServletRequest.getContentLengthLong());
    }

    @Test
    void testDoFilterInternal2() throws IOException, ServletException {
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jWTAuthenticationFilter.doFilterInternal(null, response, filterChain);
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        HttpServletResponse response1 = response.getResponse();
        assertTrue(response1 instanceof ResponseFacade);
        assertSame(response.getOutputStream(), response1.getOutputStream());
        assertTrue(jWTAuthenticationFilter.getEnvironment() instanceof StandardServletEnvironment);
    }

    @Test
    void testDoFilterInternal3() throws IOException, ServletException {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jWTAuthenticationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }

    @Test
    void testDoFilterInternal4() throws IOException, ServletException {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jWTAuthenticationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }

    @Test
    void testConstructor() {
        JWTAuthenticationFilter actualJwtAuthenticationFilter = new JWTAuthenticationFilter();
        actualJwtAuthenticationFilter.setJwtTokenProvider(new JWTTokenProvider());
        actualJwtAuthenticationFilter.setUserDetailsService(new CustomUserDetailsServiceImpl(mock(UserRepository.class)));
        Environment environment = actualJwtAuthenticationFilter.getEnvironment();
        assertTrue(environment instanceof StandardServletEnvironment);
        assertNull(actualJwtAuthenticationFilter.getFilterConfig());
        String[] activeProfiles = environment.getActiveProfiles();
        assertEquals(0, activeProfiles.length);
        assertArrayEquals(new String[]{}, activeProfiles);
        Map<String, Object> systemProperties = ((StandardServletEnvironment) environment).getSystemProperties();
        assertEquals(75, systemProperties.size());
        Map<String, Object> systemEnvironment = ((StandardServletEnvironment) environment).getSystemEnvironment();
        assertEquals(50, systemEnvironment.size());
        assertTrue(((StandardServletEnvironment) environment).getConversionService() instanceof DefaultConversionService);
        String[] defaultProfiles = environment.getDefaultProfiles();
        assertEquals(1, defaultProfiles.length);
        assertArrayEquals(new String[]{"default"}, defaultProfiles);
        assertEquals("default", defaultProfiles[0]);
        String expectedString = Paths
                .get(System.getProperty("user.home"), "AppData", "Roaming", "JetBrains", "IntelliJIdea2022.3", "plugins",
                        "diffblue-cover-ij", "META-INF", "cover-service-analyzer-2023.03.01.jar")
                .toString();
        assertEquals(expectedString, systemProperties.get("cover.jar.path"));
        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        assertEquals(4, propertySources.size());
        assertEquals("Console", systemEnvironment.get("SESSIONNAME"));
        assertEquals("23", systemEnvironment.get("PROCESSOR_LEVEL"));
        assertEquals("APAXNC", systemEnvironment.get("USERDOMAIN_ROAMINGPROFILE"));
        String expectedString1 = System.getProperty("awt.toolkit");
        assertEquals(expectedString1, systemProperties.get("awt.toolkit"));
        assertEquals("C:\\ProgramData", systemEnvironment.get("ALLUSERSPROFILE"));
        assertEquals("11", systemProperties.get("java.specification.version"));
        String expectedString2 = System.getProperty("sun.desktop");
        assertEquals(expectedString2, systemProperties.get("sun.desktop"));
        assertEquals(4L, propertySources.spliterator().getExactSizeIfKnown());
    }
}

