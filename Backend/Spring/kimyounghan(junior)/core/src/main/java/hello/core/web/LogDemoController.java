package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // request 소코프를 제공해줘야하는데 제공할 빈이 없다. -> 스프링 초기화 시기에는 request 요청이 없기 때문 (에러)
    // DI 가 될 수 없엇 탐색(DL)해야한다 -> ObjectProvider 활용
//    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    /* 프록시를 적용한뒤 첫 작성 코드 */
    private final MyLogger myLogger;


//    [6ee14107-1f48-4300-b666-549f6ea5ac44] request scope bean create : hello.core.common.MyLogger@16ab6f07
//    [6ee14107-1f48-4300-b666-549f6ea5ac44] [http://localhost:8080/log-demo] controller test
//    [6ee14107-1f48-4300-b666-549f6ea5ac44] [http://localhost:8080/log-demo] service id = testId
//    [6ee14107-1f48-4300-b666-549f6ea5ac44] request scope bean create : hello.core.common.MyLogger@16ab6f07

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        // 서블릿 request가 있을때에 ObjectProvider 객체가 MyLogger 스프링 빈을 찾아서 넣어준다.
//        MyLogger myLogger = myLoggerObjectProvider.getObject();


        String requestURL = request.getRequestURL().toString();

        // 가짜 프록시 객체를 넣어놓고 실제 myLogger 가 호출될때 진짜 객체를 찾는다
        // myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$5adb4a8a
        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }
}
