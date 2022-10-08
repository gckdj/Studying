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

// 트랜잭션 우선순위
@SpringBootTest
@RequiredArgsConstructor
public class TxLevelTest {

    @Autowired
    LevelService levelService;

    @Test
    void orderTest() {
        levelService.write();
        levelService.read();
    }

    @TestConfiguration
    static class TxLevelTextConfig {
        @Bean
        LevelService levelService() {
            return new LevelService();
        }
    }

    //2022-10-08 21:13:48.609 TRACE 14584 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.apply.TxLevelTest$LevelService.write]
    //2022-10-08 21:13:48.612  INFO 14584 --- [           main] h.s.apply.TxLevelTest$LevelService       : call write
    //2022-10-08 21:13:48.612  INFO 14584 --- [           main] h.s.apply.TxLevelTest$LevelService       : tx active = true
    //2022-10-08 21:13:48.612  INFO 14584 --- [           main] h.s.apply.TxLevelTest$LevelService       : tx readOnly = false
    //2022-10-08 21:13:48.612 TRACE 14584 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.apply.TxLevelTest$LevelService.write]
    //2022-10-08 21:13:48.613 TRACE 14584 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.apply.TxLevelTest$LevelService.read]
    //2022-10-08 21:13:48.613  INFO 14584 --- [           main] h.s.apply.TxLevelTest$LevelService       : call read
    //2022-10-08 21:13:48.613  INFO 14584 --- [           main] h.s.apply.TxLevelTest$LevelService       : tx active = true
    //2022-10-08 21:13:48.613  INFO 14584 --- [           main] h.s.apply.TxLevelTest$LevelService       : tx readOnly = true
    //2022-10-08 21:13:48.613 TRACE 14584 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.apply.TxLevelTest$LevelService.read]

    @Slf4j
    @Transactional(readOnly = true)
    static class LevelService {

        // 더 구체적인 메서드에 위치한 어노테이션이 적용
        // readOnly = false (기본값)
        @Transactional(readOnly = false)
        public void write() {
            log.info("call write");
            printTxInfo();
        }

        // 자신의 상위 클래스에 위치한 어노테이션을 탐색하고 적용
        public void read() {
            log.info("call read");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }

        // 인터페이스에도 트랜잭셔널을 적용시킬 수 있고, 우선순위는 구현체 다음이다.
        // 인터페이스에 트랜잭셔널 적용은 공식문서에서도 권장하지 않는다.
    }
}
