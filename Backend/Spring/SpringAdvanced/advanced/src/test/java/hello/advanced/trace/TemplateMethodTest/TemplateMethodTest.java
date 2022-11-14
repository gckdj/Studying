package hello.advanced.trace.TemplateMethodTest;

import hello.advanced.trace.TemplateMethodTest.code.AbstractTemplate;
import hello.advanced.trace.TemplateMethodTest.code.SubClassLogic1;
import hello.advanced.trace.TemplateMethodTest.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");

        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultItem = endTime - startTime;
        log.info("resultTime = {}", resultItem);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");

        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultItem = endTime - startTime;
        log.info("resultTime = {}", resultItem);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    // 바뀌는 부분에 따라 클래스를 생성하지 않는 방법(익명 내부클래스)
    @Test
    void templateMethodV2() {

        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };

        log.info("클래스 이름1 = {}", template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };

        log.info("클래스 이름2 = {}", template2.getClass());
        template2.execute();
    }
}
