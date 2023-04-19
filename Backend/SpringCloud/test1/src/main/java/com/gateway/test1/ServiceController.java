package com.gateway.test1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class ServiceController {
    Environment env;

    @Autowired
    public ServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("welcome")
    public String welcome() {
        return "Welcome to the first-test-server.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello World in First Service.";
    }

    // 스프링 게이트웨이에서는 라우팅지원
    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server Port={}", request.getServerPort());
        return String.format("Hi, there. This is message from First Server on Port %s", env.getProperty("local.server.port"));
    }
}
