package hello.typeconverter.controller;

import hello.typeconverter.type.ipPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); // 파라미터가 넘어오면 문자로 조회가 된다.
        Integer intValue = Integer.valueOf(data); // 숫자 타입으로 변경

        System.out.println("intValue = " + intValue);
        return "ok";
    }

    @GetMapping("/hello-v2")
    // 여태 형변환이 간편했던건 스프링이 @RequestParam 옆에 붙은 자료형으로 변환을 해주었기 때문이다.
    public String helloV2(@RequestParam Integer data) {
        // 스프링 자체에 컨버터가 내장되어 있어 컨버터가 없어도 원래 작동은 한다.
        // 단, 별도로 적용된 컨버터가 등록되면 등록된 컨버터를 우선순위로 사용한다.
        System.out.println("data = " + data);
        return "ok";
    }
    
    @GetMapping("/ip-port")
    public String ipPort(@RequestParam ipPort ipPort) {
        System.out.println("ipPort.getIp() = " + ipPort.getId());
        System.out.println("ipPort.getPort() = " + ipPort.getPort());
        return "ok";

        // 2022-07-31 20:11:06.419  INFO 24924 --- [nio-8080-exec-5] h.t.converter.StringToIpPortConverter    : convert source = 127.0.0.1:8080
        // ipPort.getIp() = 127.0.0.1
        // ipPort.getPort() = 8080
    }
}
