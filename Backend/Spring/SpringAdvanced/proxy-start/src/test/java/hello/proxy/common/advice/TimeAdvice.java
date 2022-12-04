package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // target ->
        // target 정보가 없이도 프록시가 메서드를 호출할 수 있는 것은
        // MethodInvocation 내에 관련 정보가 있기 때문이다. (프록시 팩토리에서 생성하면서 입력)
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = startTime - endTime;
        log.info("TimeProxy 종료 resultTime = {}", resultTime);
        return result;
    }
}
