package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    // 스프링부트가 빈등록과정에서 빈후처리기를 자동으로 생성
    // @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advise
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
    // 스프링 초기화 시점에 빈이 등록되는데, 이때 어드바이저는 포인트컷을 기준으로
    // 어드바이스를 적용할 가능성이 있는 곳에만 프록시를 생성한다.

    // 포인트컷 기준에 해당되기 때문에 의도치 않던 프록시 객체가 생성되기도 한다.
    // => 아주 정밀한 포인트컷 기준을 마련하는 것이 필요
    //INFO 1903 --- [           main] h.p.trace.logtrace.ThreadLocalLogTrace   : [a611d82f] EnableWebMvcConfiguration.requestMappingHandlerAdapter()
    //INFO 1903 --- [           main] h.p.trace.logtrace.ThreadLocalLogTrace   : [a611d82f] EnableWebMvcConfiguration.requestMappingHandlerAdapter() time=27ms
    //INFO 1903 --- [           main] h.p.trace.logtrace.ThreadLocalLogTrace   : [5c5a9f97] EnableWebMvcConfiguration.requestMappingHandlerMapping()
    //INFO 1903 --- [           main] h.p.trace.logtrace.ThreadLocalLogTrace   : [5c5a9f97] EnableWebMvcConfiguration.requestMappingHandlerMapping() time=3ms

    // aspectJ 포인트컷 활용
    //@Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");

        //advise
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // no-log 문제해결
    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // !execution -> 대상메서드 제외
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");

        //advise
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
