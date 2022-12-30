package hello.proxy.config.v6_aop;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v6_aop.aspect.LogTraceAspect;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    // 실무선 횡단관심사 적용방법 -> 에노테이션 방식채택

    // 핵심기능 : 해당 객체의 고유기능
    // 부가기능 : 로깅 등 공통기능
    // 부가기능 적용의 문제점 : 많은 반복 => 중복 코드, 수정시 과한소요
    // 해결 : 모듈화 (Aspect-Oriented Programming), 관리 일원화
}
