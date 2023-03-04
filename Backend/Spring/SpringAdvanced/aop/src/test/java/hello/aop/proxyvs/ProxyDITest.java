package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // true: cglib, false: jdk 동적프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    // 인터페이스 주입
    @Autowired
    MemberService memberService;

    // 구체타입 주입
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        // 인터페이스만 참조하는 jdk 동적프록시는 구체클래스를 받을 수 없다.
        // log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        // memberServiceImpl.hello("hello");

        // 구체클래스를 상속받아서 생성되는 cglib는 구체클래스를 받을 수 있다.
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}

