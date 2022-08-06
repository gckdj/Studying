package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        // 마지막에 접근한 시간 중요사용
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        //2022-07-07 22:13:36.694  INFO 11692 --- [nio-8080-exec-2] h.l.web.session.SessionInfoController    : sessionId=D963CBCFAFA3D9AC344707EBCB6B0A20
        //2022-07-07 22:13:36.694  INFO 11692 --- [nio-8080-exec-2] h.l.web.session.SessionInfoController    : getMaxInactiveInterval=1800
        //2022-07-07 22:13:36.694  INFO 11692 --- [nio-8080-exec-2] h.l.web.session.SessionInfoController    : creationTime=Thu Jul 07 22:13:26 KST 2022
        //2022-07-07 22:13:36.694  INFO 11692 --- [nio-8080-exec-2] h.l.web.session.SessionInfoController    : lastAccessedTime=Thu Jul 07 22:13:26 KST 2022
        //2022-07-07 22:13:36.694  INFO 11692 --- [nio-8080-exec-2] h.l.web.session.SessionInfoController    : isNew=false

        // 서버입장에서는 클라이언트가 접속을 종료했는지 알 수 없음(비연결성)
        // 서버에서 세션을 누적해서 가지고 있다면 메모리가 무한히 누적되는 것...
        // 또한 세션이 계속 유지된다면 노출된 세션아이디로 악의적인 요청을 할 수 있다.
        // -> 모든 세션은 생성된 이후 적절한 시점에 만료시켜 서비스 최적화 필요
        // 단순히 첫 접속시간으로부터 30분에 만료시키게 되면 서비스 이용 중에 접속이 끊어지게 된다.
        // 대신, HttpSession은 마지막 http 요청에 맞춤으로 30분씩 만료시간을 부여하는 방식을 사용한다.
        return "세션 출력";
    }
}
