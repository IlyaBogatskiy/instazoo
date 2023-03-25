package com.ibdev.instazoo.service.interfaces;

import com.ibdev.instazoo.dto.UserDTO;
import com.ibdev.instazoo.model.User;
import com.ibdev.instazoo.payload.request.SignUpRequest;

import java.security.Principal;

public interface UserService {

    User createUser(SignUpRequest userIn);

    User updateUser(UserDTO userDTO, Principal principal);

    User getCurrentUser(Principal principal);

    User getUserById(Long id);
}
