package com.gateway.test2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
public class Controller {

    @GetMapping("welcome")
    public String welcome() {
        return "Welcome to the second-test-server.";
    }
}
