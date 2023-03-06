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


    // CGLIB 한계 및 스프링의 대안
    // 한계점: 생성자 2번 호출 (1. target 인스턴스 생성 2. 프록시객체 생성 시 부모클래스 생성자호출[자바])
    // 대안: 스프링 4.0 기본 패키지 'objenesis' -> 생성자 호출없이 프록시객체 생성
    // 최종: 스프링부트 2.0 이후 프록시 기본값에 CGLIB 채택
}

