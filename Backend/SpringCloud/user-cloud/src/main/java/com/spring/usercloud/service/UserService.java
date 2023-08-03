package com.spring.usercloud.service;

import com.spring.usercloud.dto.UserDTO;
import com.spring.usercloud.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userId);

    // 디비에서 가져온 데이터 바로사용
    Iterable<UserEntity> getUserByAll();

    UserDTO getUserDetailsByEmail(String username);
}
