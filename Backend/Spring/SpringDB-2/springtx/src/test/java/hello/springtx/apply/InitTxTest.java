package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@SpringBootTest
@Slf4j
public class InitTxTest {


    @Autowired Hello hello;

    // 메서드내 별도 명시안해도 @PostConstruct가 붙은 메서드 실행
    @Test
    void go() {

    }

    @TestConfiguration
    static class InitTxTestConfig {

        @Bean
        Hello hello() {
            return new Hello();
        }
    }

    static class Hello {

        // @PostConstruct와 @Transactional이 동시에 사용되면 트랜잭션 적용이 안된다.
        @PostConstruct
        @Transactional
        public void initV1() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("hello init @PostContruct tx active = {}", isActive);
        }
        //2022-10-10 18:42:33.854  INFO 5014 --- [           main] hello.springtx.apply.InitTxTest          : hello init @PostContruct tx active = false

        // @EventListener는 특정 시점에서 메서드를 실행할 수 있도록한다.
        // ApplicationsReadyEvent는 스프링 컨테이너가 구성된 이후를 뜻한다.
        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init ApplicationReadyEvent tx active = {}", isActive);
        }
        //2022-10-10 18:42:33.955 TRACE 5014 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.apply.InitTxTest$Hello.initV2]
        //2022-10-10 18:42:33.958  INFO 5014 --- [           main] hello.springtx.apply.InitTxTest          : Hello init ApplicationReadyEvent tx active = true
        //2022-10-10 18:42:33.959 TRACE 5014 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.apply.InitTxTest$Hello.initV2]
    }
}
