package com.spring.usercloud.controller;

import com.spring.usercloud.dto.UserDTO;
import com.spring.usercloud.jpa.UserEntity;
import com.spring.usercloud.service.UserService;
import com.spring.usercloud.vo.Greeting;
import com.spring.usercloud.vo.RequestUser;
import com.spring.usercloud.vo.ResponseUser;
import io.micrometer.core.annotation.Timed;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// 게이트웨이에서 라우팅된 경로를 받도록 내용추가
public class UserController {

    private final Environment env;
    private final UserService userService;

    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @Autowired
    private Greeting greeting;

    @GetMapping("health_check")
    @Timed(value = "users.status", longTask = true)
    public String status() {
        // return "It's Working in User Service";
        // 로컬 구성정보 제거 -> config server 구성정보 사용
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("local.token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("welcome")
    @Timed(value = "users.welcome", longTask = true)
    public String welcome() {
        // 환경변수에서 꺼내기
        // return env.getProperty("greeting.message");
        // Value 어노테이션을 통해 값을 가진 vo 활용
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDto = mapper.map(user, UserDTO.class);

        System.out.println(userDto);
        userService.createUser(userDto);

        ResponseUser res = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> users = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        users.forEach(u -> {
            result.add(mapper.map(u, ResponseUser.class));
        });

        System.out.println(result);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ResponseUser>> getUsers(@PathVariable("userId") String userId) {
        UserDTO findUser = userService.getUserByUserId(userId);
        List<ResponseUser> result = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        result.add(mapper.map(findUser, ResponseUser.class));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}