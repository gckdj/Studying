package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

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
    //트랜잭셔널로 인해 트랜잭션 프록시가 호출되어있음
    //2022-10-09 20:08:37.370  INFO 9023 --- [           main] hello.springtx.apply.InternalCallV1Test  : callService class = class hello.springtx.apply.InternalCallV1Test$CallService$$EnhancerBySpringCGLIB$$6a7c9c13

    @Test
    void externalCall() {
        callService.external();
    }
    //트랜잭셔널이 없는 external에서 internal을 호출해도 트랜잭셔널이 적용 X
    //2022-10-09 20:10:47.180  INFO 9123 --- [           main] h.s.a.InternalCallV1Test$CallService     : call external
    //2022-10-09 20:10:47.180  INFO 9123 --- [           main] h.s.a.InternalCallV1Test$CallService     : tx active = false
    //2022-10-09 20:10:47.180  INFO 9123 --- [           main] h.s.a.InternalCallV1Test$CallService     : call internal
    //2022-10-09 20:10:47.180  INFO 9123 --- [           main] h.s.a.InternalCallV1Test$CallService     : tx active = false

    //트랜잭션 프록시 호출 단계를 넘어간 상태에서 internal이 호출되었기 때문에 실제 target의 internal을 호출하게됨
    //스프링 AOP의 한계 : 메서드 내부 호출에는 프록시를 적용할 수 없다.

    @TestConfiguration
    static class InternalCallV1TestConfig {

        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {

        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
        }

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
}
