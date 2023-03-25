package com.ibdev.instazoo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ibdev.instazoo.dto.UserDTO;
import com.ibdev.instazoo.model.Post;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.model.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserFacade.class})
@ExtendWith(SpringExtension.class)
class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;

    @Test
    void testUserToUserDTO() {
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
        UserDTO actualUserToUserDTOResult = userFacade.userToUserDTO(user);
        assertEquals("Bio", actualUserToUserDTOResult.getBio());
        assertEquals("janedoe", actualUserToUserDTOResult.getUsername());
        assertEquals("Doe", actualUserToUserDTOResult.getLastname());
        assertEquals(1L, actualUserToUserDTOResult.getId().longValue());
        assertEquals("Name", actualUserToUserDTOResult.getFirstname());
    }

    @Test
    void testUserToUserDTO2() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getBio()).thenReturn("Bio");
        when(user.getLastname()).thenReturn("Doe");
        when(user.getName()).thenReturn("Name");
        when(user.getUsername()).thenReturn("janedoe");
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
        UserDTO actualUserToUserDTOResult = userFacade.userToUserDTO(user);
        assertEquals("Bio", actualUserToUserDTOResult.getBio());
        assertEquals("janedoe", actualUserToUserDTOResult.getUsername());
        assertEquals("Doe", actualUserToUserDTOResult.getLastname());
        assertEquals(1L, actualUserToUserDTOResult.getId().longValue());
        assertEquals("Name", actualUserToUserDTOResult.getFirstname());
        verify(user).getId();
        verify(user).getBio();
        verify(user).getLastname();
        verify(user).getName();
        verify(user).getUsername();
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

