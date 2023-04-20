package com.microservice.user.service;

import com.microservice.user.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
}
