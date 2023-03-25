package com.ibdev.instazoo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.model.enums.Role;
import com.ibdev.instazoo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.ibdev.instazoo.service.impl.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomUserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        user.setAuthorities(grantedAuthorityList);
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
        when(userRepository.findUserByEmail((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = customUserDetailsService.loadUserByUsername("janedoe");
        assertEquals(grantedAuthorityList, actualLoadUserByUsernameResult.getAuthorities());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(((User) actualLoadUserByUsernameResult).getRoles().isEmpty());
        assertEquals(grantedAuthorityList, ((User) actualLoadUserByUsernameResult).getPosts());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals(1L, ((User) actualLoadUserByUsernameResult).getId().longValue());
        assertEquals("jane.doe@example.org", ((User) actualLoadUserByUsernameResult).getEmail());
        verify(userRepository).findUserByEmail((String) any());
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        HashSet<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);

        User user = new User();
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        user.setAuthorities(grantedAuthorityList);
        user.setBio("Bio");
        user.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setLastname("Doe");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRoles(roleSet);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByEmail((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = customUserDetailsService.loadUserByUsername("janedoe");
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(((User) actualLoadUserByUsernameResult).getRoles().isEmpty());
        assertEquals(grantedAuthorityList, ((User) actualLoadUserByUsernameResult).getPosts());
        assertEquals("jane.doe@example.org", ((User) actualLoadUserByUsernameResult).getEmail());
        assertEquals(1L, ((User) actualLoadUserByUsernameResult).getId().longValue());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("USER", ((List<? extends GrantedAuthority>) authorities).get(0).getAuthority());
        verify(userRepository).findUserByEmail((String) any());
    }

    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        HashSet<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ADMIN);
        roleSet.add(Role.USER);

        User user = new User();
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        user.setAuthorities(grantedAuthorityList);
        user.setBio("Bio");
        user.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setLastname("Doe");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setPosts(new ArrayList<>());
        user.setRoles(roleSet);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByEmail((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = customUserDetailsService.loadUserByUsername("janedoe");
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertEquals(2, authorities.size());
        assertEquals("jane.doe@example.org", ((User) actualLoadUserByUsernameResult).getEmail());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(((User) actualLoadUserByUsernameResult).getRoles().isEmpty());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals(grantedAuthorityList, ((User) actualLoadUserByUsernameResult).getPosts());
        assertEquals(1L, ((User) actualLoadUserByUsernameResult).getId().longValue());
        assertEquals("ADMIN", ((List<? extends GrantedAuthority>) authorities).get(0).toString());
        assertEquals("USER", ((List<? extends GrantedAuthority>) authorities).get(1).toString());
        verify(userRepository).findUserByEmail((String) any());
    }

    @Test
    void testLoadUserByUsername4() throws UsernameNotFoundException {
        when(userRepository.findUserByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("janedoe"));
        verify(userRepository).findUserByEmail((String) any());
    }

    @Test
    void testLoadUserByUsername5() throws UsernameNotFoundException {
        when(userRepository.findUserByEmail((String) any()))
                .thenThrow(new UsernameNotFoundException("Loading user with username: {}"));
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("janedoe"));
        verify(userRepository).findUserByEmail((String) any());
    }

    @Test
    void testLoadUserById() throws UsernameNotFoundException {
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
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(user, customUserDetailsService.loadUserById(1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testLoadUserById2() throws UsernameNotFoundException {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserById(1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testLoadUserById3() throws UsernameNotFoundException {
        when(userRepository.findById((Long) any())).thenThrow(new UsernameNotFoundException("Loading user with id: {}"));
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserById(1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testBuild() {
        CustomUserDetailsServiceImpl customUserDetailsService = new CustomUserDetailsServiceImpl(mock(UserRepository.class));

        User user = new User();
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        user.setAuthorities(grantedAuthorityList);
        user.setBio("Bio");
        user.setCreateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setLastname("Doe");
        user.setName("Name");
        user.setPassword("iloveyou");
        ArrayList<Post> postList = new ArrayList<>();
        user.setPosts(postList);
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        User actualBuildResult = customUserDetailsService.build(user);
        Collection<? extends GrantedAuthority> authorities = actualBuildResult.getAuthorities();
        assertEquals(grantedAuthorityList, authorities);
        assertEquals(postList, authorities);
        assertTrue(authorities.isEmpty());
        assertTrue(actualBuildResult.isEnabled());
        assertTrue(actualBuildResult.isCredentialsNonExpired());
        assertTrue(actualBuildResult.isAccountNonLocked());
        assertTrue(actualBuildResult.isAccountNonExpired());
        assertEquals("janedoe", actualBuildResult.getUsername());
        assertTrue(actualBuildResult.getRoles().isEmpty());
        List<Post> posts = actualBuildResult.getPosts();
        assertEquals(grantedAuthorityList, posts);
        assertEquals(postList, posts);
        assertEquals(authorities, posts);
        assertTrue(posts.isEmpty());
        assertEquals("iloveyou", actualBuildResult.getPassword());
        assertNull(actualBuildResult.getName());
        assertNull(actualBuildResult.getLastname());
        assertEquals(1L, actualBuildResult.getId().longValue());
        assertEquals("jane.doe@example.org", actualBuildResult.getEmail());
        assertNull(actualBuildResult.getCreateDate());
        assertNull(actualBuildResult.getBio());
    }
}

