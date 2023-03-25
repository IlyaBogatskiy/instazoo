package com.ibdev.instazoo.service.interfaces;

import com.ibdev.instazoo.model.User;

public interface CustomUserDetailsService {

    User loadUserById(Long id);

    User build(User user);
}
