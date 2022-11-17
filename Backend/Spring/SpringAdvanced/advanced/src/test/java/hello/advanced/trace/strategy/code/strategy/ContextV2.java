package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

// 전략클래스를 주입받지 않고 파라미터로 넘기는 방식
@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();

        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
