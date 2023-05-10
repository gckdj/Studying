package com.microservice.user.controller;

import com.microservice.user.dto.UserDTO;
import com.microservice.user.jpa.UserEntity;
import com.microservice.user.service.UserService;
import com.microservice.user.vo.Greeting;
import com.microservice.user.vo.RequestUser;
import com.microservice.user.vo.ResponseUser;
import org.apache.catalina.User;
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
    public String status() {
        // return "It's Working in User Service";
        return String.format("It's Working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("welcome")
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

    @GetMapping("/users/{userid}")
    public ResponseEntity<List<ResponseUser>> getUsers(@PathVariable("userId") String userId) {
        UserDTO findUser = userService.getUserByUserId(userId);
        List<ResponseUser> result = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        result.add(mapper.map(findUser, ResponseUser.class));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}