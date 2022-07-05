package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.*;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        // 단위테스트에서 http 요청, 응답은 인터페이스로 구현체는 톰캣이 제공
        // 이로인해, 스프링에서는 목서블릿을 제공

        // 세션생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션만료
        sessionManager.expireSession(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}
