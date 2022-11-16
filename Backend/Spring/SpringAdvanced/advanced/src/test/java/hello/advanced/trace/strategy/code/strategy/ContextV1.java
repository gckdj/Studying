package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/*
* 필드에 전략을 보관하는 방식
* */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();

        // 인터페이스에 메서드호출 위임
        // 필요한 부분만 인터페이스에 의존하고 있어, 추후 로직의 변경이 일어난다해도
        // 현재 클래스 내 코드를 변경할 필요 X
        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
