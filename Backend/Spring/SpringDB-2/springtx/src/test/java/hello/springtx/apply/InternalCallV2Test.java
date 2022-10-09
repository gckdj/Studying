package hello.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {

    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        log.info("callService class = {}", callService.getClass());
    }

    @Test
    void internalCall() {
        callService.internal();
    }

    @Test
    void externalCall() {
        callService.external();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig {

        @Bean
        CallService callService() {
            return new CallService(internalService());
        }

        @Bean
        InternalService internalService() {
            return new InternalService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService {

        private final InternalService internalService;

        public void external() {
            log.info("call external");
            printTxInfo();
            //외부에서 주입받은 객체에서 internal을 호출
            internalService.internal();
        }

        //2022-10-09 20:22:36.441 TRACE 9635 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.apply.InternalCallV2Test$CallService.internal]
        //2022-10-09 20:22:36.441  INFO 9635 --- [           main] h.s.a.InternalCallV2Test$CallService     : call internal
        //2022-10-09 20:22:36.441  INFO 9635 --- [           main] h.s.a.InternalCallV2Test$CallService     : tx active = true
        //2022-10-09 20:22:36.441 TRACE 9635 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.apply.InternalCallV2Test$CallService.internal]

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }
    }

    // 프록시를 거치지않는 내부호출을 방지하기 위해 external과 internal 메서드를 별도의 클래스로 분리
    static class InternalService {

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }
    }

    // 참고 : public 메서드에만 트랜잭션이 적용된다.
}
