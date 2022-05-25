package hello.springmvcbasic.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller : 반환값을 뷰의 이름으로 처리
// @RestController : 반환값은 Http 메세지 바디를 통한 응답처리
// lombok 사용 : @Slf4j
@RestController
public class LogTestController {
    // getLogger 내에 현재 클래스 정보를 넘겨주기
    // lombok 사용 : @Slf4j
    private final Logger log = LoggerFactory.getLogger(LogTestController.class);
    
    @RequestMapping("/log-test")
    public String logTest() {
        String name = "String";

        // 여태까지는 이렇게
        System.out.println("name = " + name);
        // 앞으로는 이렇게
        log.info("info log = {}", name);

        // sysout으로 찍은건 단순 스트링이 끝
        // name = String
        // log로 찍으면 패키지, 클래스까지 나온다
        // 2022-05-25 21:13:41.455  INFO 11372 --- [nio-8080-exec-1] h.s.basic.LogTestController              : info log = String

        // 각 로그에 대한 중요도를 표시할 수 있음
        // 로깅레벨보다 낮은 로깅 연산 X
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.warn(("warn log={}"), name);
        log.error("error log={}", name);

        // 운영서버에 debug 이상의 로깅레벨 유지 X
        // 로컬 : trace, 개발 : debug, 운영 : info

        // level info
        // 2022-05-25 21:19:21.634  INFO 17012 --- [nio-8080-exec-1] h.s.basic.LogTestController              : info log = String
        // 2022-05-25 21:19:21.635  WARN 17012 --- [nio-8080-exec-1] h.s.basic.LogTestController              : warn log=String
        // 2022-05-25 21:19:21.635 ERROR 17012 --- [nio-8080-exec-1] h.s.basic.LogTestController              : error log=String

        // 각 로그들은 파일로 저장 혹은 네트워크 전송가능
        return "ok";
    }
}
