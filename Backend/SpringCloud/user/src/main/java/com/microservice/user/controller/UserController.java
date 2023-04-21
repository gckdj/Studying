package com.microservice.user.controller;

import com.microservice.user.dto.UserDTO;
import com.microservice.user.service.UserService;
import com.microservice.user.vo.Greeting;
import com.microservice.user.vo.RequestUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
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
        return "It's Working in User Service";
    }

    @GetMapping("welcome")
    public String welcome() {
        // 환경변수에서 꺼내기
        // return env.getProperty("greeting.message");
        // Value 어노테이션을 통해 값을 가진 vo 활용
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDto = mapper.map(user, UserDTO.class);

        System.out.println(userDto);
        userService.createUser(userDto);
        return "create user method is called";
    }
}