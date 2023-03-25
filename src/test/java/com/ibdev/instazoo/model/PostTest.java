package com.ibdev.instazoo.model;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ibdev.instazoo.model.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class PostTest {

    @Test
    void testOnCreate2() {
        User user = mock(User.class);
        doNothing().when(user).setAuthorities((Collection<GrantedAuthority>) any());
        doNothing().when(user).setBio((String) any());
        doNothing().when(user).setCreateDate((LocalDateTime) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setLastname((String) any());
        doNothing().when(user).setName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setPosts((List<Post>) any());
        doNothing().when(user).setRoles((Set<Role>) any());
        doNothing().when(user).setUsername((String) any());
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

        Post post = new Post();
        post.setUser(user);
        post.onCreate();
        verify(user).setAuthorities((Collection<GrantedAuthority>) any());
        verify(user).setBio((String) any());
        verify(user).setCreateDate((LocalDateTime) any());
        verify(user).setEmail((String) any());
        verify(user).setId((Long) any());
        verify(user).setLastname((String) any());
        verify(user).setName((String) any());
        verify(user).setPassword((String) any());
        verify(user).setPosts((List<Post>) any());
        verify(user).setRoles((Set<Role>) any());
        verify(user).setUsername((String) any());
    }
}

