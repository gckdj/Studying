package hello.advanced.trace.TemplateMethodTest.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    // 템플릿 패턴 구현
    // 템플릿 내에서 변하지 않는 부분을 몰아두고
    // 일부 변하는 부분을 별도로 호출해서 해결
    public void execute() {
        long startTime = System.currentTimeMillis();

        call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    protected abstract void call();
}
