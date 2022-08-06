package hello.springmvcbasic.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
/* 단순 텍스트 받기 */
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        response.getWriter().write("ok");
        //2022-06-01 14:49:18.680  INFO 9512 --- [nio-8080-exec-6] h.s.b.r.RequestBodyStringController      : messageBody = hello
    }

    /* 스프링에서 제공하는 InputStream, OutputStream 객체를 활용하면 HTTP 요청, 응답 메시지 바디의 내용을 직접 조회, 출력 */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString((inputStream), StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);

        responseWriter.write("ok");
    }

    /* HttpEntity로 메세지헤더와 바디를 편리하게 읽기, 쓰기 지원 */
    /* 요청파라미터를 조회하는 기능과는 전혀관계가 없다 */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        log.info("messageBody = {}", body);

        return new HttpEntity<>("ok");
    }

    //    RequestEntity, ResponseBody 조합은 상태코드도 반환가능
    //    @PostMapping("/request-body-string-v3")
    //    public HttpEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity) throws IOException {
    //        String body = httpEntity.getBody();
    //        log.info("messagebody = {}", body);
    //
    //        return new ResponseBody<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //    }

    // 가장 트렌디한 방법
    // @RequestBody
    // 헤더정보가 필요한 경우에는 HttpEntity 또는 @RequestHeader 사용
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody = {}", messageBody);
        return "ok";
    }
}
