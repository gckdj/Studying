package hello.springmvcbasic.basic.request;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.springmvcbasic.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestParamController {
    
    /* 전통적인 방식의 Parameter 받는 방법 */
    /* 객체를 만들고 객체에 파라미터로 받은 값을 넣는 방식 → 스프링에서는 ModelAttribute로 자동화 */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        
        response.getWriter().write("ok");
    }
    
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }
    
    // @ResponseBody
    // @RequestMapping("/model-attribute-v1")
    // public String modelAttributeV1(@RequestParam String username, @RequestMapping int age) {
    //     HelloData helloData = new HelloData();
    //     helloData.setUsername(username);
    //     helloData.setAge(age);
        
    //     log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
    //     log.info("helloData = {}", helloData.toString());
    // }
    
    /* @RequestParam 에노테이션으로 파라미터를 받고 객체생성, 값을 넣어주는 과정이 필요없음 */
    /* @ModelAttribute로 파라미터를 객체의 프로퍼티와 같은 이름에 붙여줌 */
    /* 자료형을 맞지 않게 입력을 하게되면, 바인딩 예외가 발생한다. */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
    
    /* ModelAttribute는 생략가능 */
    /* 스프링은 String, int, Integer와 같은 자료형은 @RequestParam으로 파라미터를 받고, */
    /* 그 외에는 ModelAttribute를 적용 */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
 }
