package com.microservice.user.service;

import com.microservice.user.dto.UserDTO;
import com.microservice.user.jpa.UserEntity;
import com.microservice.user.jpa.UserRepository;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);

        // 사용자암호화 -> 스프링시큐리티 활용
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));

        userRepository.save(userEntity);

        UserDTO rtUserDto = mapper.map(userEntity, UserDTO.class);
        return rtUserDto;
    }
}
