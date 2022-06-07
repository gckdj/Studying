package hello.springmvcbasic.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvcbasic.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
/* 메세지바디 내 Json 데이터처리법 */
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
        //2022-06-01 15:07:26.589  INFO 10460 --- [nio-8080-exec-1] h.s.b.request.RequestBodyJsonController  : messageBody = {"username" : "hello", "age" : "12"}
        //2022-06-01 15:07:26.625  INFO 10460 --- [nio-8080-exec-1] h.s.b.request.RequestBodyJsonController  : username = hello, age = 12
    }

    /* @ResponseBody 안붙이면 View 찾는다 주의 */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /* @RequestBody에 직접 만든 객체를 파라미터로 받는 경우 */
    /* HTTP 메세지 컨버터가 자동으로 객체 내 프로퍼티에 맞춰 값을 받음 */
    /* @RequestBody를 생략하는 경우 @ModelAttribute를 기본으로 붙인다. 생략불가 */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData helloData = httpEntity.getBody();
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        /* Json 형식으로 받고, Json 형식으로 데이터 반환 */
        return helloData;
    }
    //{
    //    "username": "hello",
    //    "age": 12
    //}
}
