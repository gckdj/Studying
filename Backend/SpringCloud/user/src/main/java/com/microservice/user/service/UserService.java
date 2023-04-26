package com.microservice.user.service;

import com.microservice.user.dto.UserDTO;
import com.microservice.user.jpa.UserEntity;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userId);

    // 디비에서 가져온 데이터 바로사용
    Iterable<UserEntity> getUserByAll();
}
