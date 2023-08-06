package com.spring.usercloud.service;

import com.spring.usercloud.client.OrderServiceClient;
import com.spring.usercloud.dto.UserDTO;
import com.spring.usercloud.jpa.UserEntity;
import com.spring.usercloud.jpa.UserRepository;
import com.spring.usercloud.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    public static Environment env;
//    public static RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;

    CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);

        // 사용자암호화 -> 스프링시큐리티 활용
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));

        userRepository.save(userEntity);
        return mapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDTO userDto = new ModelMapper().map(userEntity, UserDTO.class);
        // List<ResponseOrder> orders = new ArrayList<>();

        // using as rest template
        // feign client 사용으로 주석
        /*String orderUrl = String.format(env.getProperty("order_service.url"), userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse =
            restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {

                });*/
        // List<ResponseOrder> ordersList = orderListResponse.getBody();

        // using as feign client
        // rest template의 역할이 인터페이스의 메소드를 통해 간단히 표현된다.

        /*
        // Feign exception 처리
        try {
            ordersList = orderServiceClient.getOrders(userId);
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }*/

        // ErrorDecoder 사용해 예외처리
        // List<ResponseOrder> ordersList = orderServiceClient.getOrders(userId);

        // Using Circuit Breaker

        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        // getOrders 결과값 존재 X -> 빈 리스트 반환
        List<ResponseOrder> ordersList = circuitbreaker.run(() -> orderServiceClient.getOrders(userId),
            throwable -> new ArrayList<>());

        userDto.setOrders(ordersList);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        // userEntity 존재하지 않는 경우
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        // 마지막 list => 사용자권한 리스트
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        // 엔티티가 없는 경우 예외발생
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new ModelMapper().map(userEntity, UserDTO.class);
    }
}
