package com.ibdev.instazoo.security.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.ibdev.instazoo.repository.UserRepository;
import com.ibdev.instazoo.security.component.JWTAuthenticationEntryPoint;
import com.ibdev.instazoo.service.impl.CustomUserDetailsServiceImpl;

import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.support.StandardServletEnvironment;

@ContextConfiguration(classes = {SecurityConfig.class})
@ExtendWith(SpringExtension.class)
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    void testConfigure2() {
        JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JWTAuthenticationEntryPoint();
        SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationEntryPoint,
                new CustomUserDetailsServiceImpl(mock(UserRepository.class)));
        securityConfig.configure((AuthenticationManagerBuilder) null);
        Environment environment = securityConfig.jwtAuthenticationFilter().getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        assertEquals(0, activeProfiles.length);
        assertArrayEquals(new String[]{}, activeProfiles);
        Map<String, Object> systemProperties = ((StandardServletEnvironment) environment).getSystemProperties();
        assertEquals(75, systemProperties.size());
        Map<String, Object> systemEnvironment = ((StandardServletEnvironment) environment).getSystemEnvironment();
        assertEquals(50, systemEnvironment.size());
        String[] defaultProfiles = environment.getDefaultProfiles();
        assertEquals(1, defaultProfiles.length);
        assertArrayEquals(new String[]{"default"}, defaultProfiles);
        assertEquals("default", defaultProfiles[0]);
        String expectedString = System.getProperty("sun.desktop");
        assertEquals(expectedString, systemProperties.get("sun.desktop"));
        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        assertEquals(4, propertySources.size());
        assertEquals("23", systemEnvironment.get("PROCESSOR_LEVEL"));
        assertEquals("APAXNC", systemEnvironment.get("USERDOMAIN_ROAMINGPROFILE"));
        String expectedString1 = Paths
                .get(System.getProperty("user.home"), "AppData", "Roaming", "JetBrains", "IntelliJIdea2022.3", "plugins",
                        "diffblue-cover-ij", "META-INF", "cover-service-analyzer-2023.03.01.jar")
                .toString();
        assertEquals(expectedString1, systemProperties.get("cover.jar.path"));
        String expectedString2 = System.getProperty("awt.toolkit");
        assertEquals(expectedString2, systemProperties.get("awt.toolkit"));
        assertEquals("Console", systemEnvironment.get("SESSIONNAME"));
        assertEquals(4L, propertySources.spliterator().getExactSizeIfKnown());
    }

    @Test
    void testBCryptPasswordEncoder() {
        JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JWTAuthenticationEntryPoint();
        SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationEntryPoint,
                new CustomUserDetailsServiceImpl(mock(UserRepository.class)));
        securityConfig.bCryptPasswordEncoder();
        Environment environment = securityConfig.jwtAuthenticationFilter().getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        assertEquals(0, activeProfiles.length);
        assertArrayEquals(new String[]{}, activeProfiles);
        Map<String, Object> systemProperties = ((StandardServletEnvironment) environment).getSystemProperties();
        assertEquals(75, systemProperties.size());
        Map<String, Object> systemEnvironment = ((StandardServletEnvironment) environment).getSystemEnvironment();
        assertEquals(50, systemEnvironment.size());
        String[] defaultProfiles = environment.getDefaultProfiles();
        assertEquals(1, defaultProfiles.length);
        assertArrayEquals(new String[]{"default"}, defaultProfiles);
        assertEquals("default", defaultProfiles[0]);
        String expectedString = System.getProperty("sun.desktop");
        assertEquals(expectedString, systemProperties.get("sun.desktop"));
        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        assertEquals(4, propertySources.size());
        assertEquals("23", systemEnvironment.get("PROCESSOR_LEVEL"));
        assertEquals("APAXNC", systemEnvironment.get("USERDOMAIN_ROAMINGPROFILE"));
        String expectedString1 = Paths
                .get(System.getProperty("user.home"), "AppData", "Roaming", "JetBrains", "IntelliJIdea2022.3", "plugins",
                        "diffblue-cover-ij", "META-INF", "cover-service-analyzer-2023.03.01.jar")
                .toString();
        assertEquals(expectedString1, systemProperties.get("cover.jar.path"));
        String expectedString2 = System.getProperty("awt.toolkit");
        assertEquals(expectedString2, systemProperties.get("awt.toolkit"));
        assertEquals("Console", systemEnvironment.get("SESSIONNAME"));
        assertEquals(4L, propertySources.spliterator().getExactSizeIfKnown());
    }
}

