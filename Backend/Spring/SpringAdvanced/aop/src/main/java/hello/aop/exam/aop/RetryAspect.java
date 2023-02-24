package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    /*@Around("@annotation(hello.aop.exam.annotation.Retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint) {
    }*/

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {}, retry={}", joinPoint.getSignature(), retry);
        int maxRetry = retry.value();

        // 예외가 발생하면 재시도
        Exception exceptionHolder = null;
        for (int retryCount = 0; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count = {}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                // 최대 시도횟수를 넘어서면 예외를 던지기 위해 담아두기
                exceptionHolder = e;

            }
        }

        throw exceptionHolder;
    }
}
