package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {

        // 데이터소스를 자동으로 등록해주지만, 작성된 코드가 있다면 우선한다.
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        //2022-10-12 21:31:36.876 DEBUG 2588 --- [           main] o.s.j.d.DataSourceTransactionManager     : Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        //2022-10-12 21:31:36.877 DEBUG 2588 --- [           main] o.s.j.d.DataSourceTransactionManager     : Initiating transaction commit
        //2022-10-12 21:31:36.877 DEBUG 2588 --- [           main] o.s.j.d.DataSourceTransactionManager     : Committing JDBC transaction on Connection [HikariProxyConnection@1348109698 wrapping conn0: url=jdbc:h2:mem:057449dd-f62e-4120-96be-c3e41adb2949 user=SA]
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        //2022-10-12 21:33:51.029 DEBUG 2695 --- [           main] o.s.j.d.DataSourceTransactionManager     : Initiating transaction rollback
        txManager.rollback(status);
        log.info("트랜잭션 커밋 완료");
        //2022-10-12 21:33:51.029  INFO 2695 --- [           main] hello.springtx.propagation.BasicTxTest   : 트랜잭션 커밋 완료
    }

    @Test
    void double_commit() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋");
        // txManager.commit(tx2);
        txManager.rollback(tx2);

        //2022-10-14 23:13:27.905 DEBUG 3097 --- [           main] o.s.j.d.DataSourceTransactionManager     : Releasing JDBC Connection [HikariProxyConnection@1247783800 wrapping conn0: url=jdbc:h2:mem:eb2fc3e5-3c1c-4e55-9ab9-9b51412741e3 user=SA] after transaction
        //2022-10-14 23:13:27.906 DEBUG 3097 --- [           main] o.s.j.d.DataSourceTransactionManager     : Switching JDBC Connection [HikariProxyConnection@1286393023 wrapping conn0: url=jdbc:h2:mem:eb2fc3e5-3c1c-4e55-9ab9-9b51412741e3 user=SA] to manual commit
    }

    @Test
    void inner_commit() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction());

        // 외부에서 시작된 트랜잭션이 내부 트랜잭션까지 묶는다.
        inner();
        //2022-10-15 23:47:57.935 DEBUG 4292 --- [           main] o.s.j.d.DataSourceTransactionManager     : Participating in existing transaction
        //2022-10-15 23:46:48.064  INFO 4241 --- [           main] hello.springtx.propagation.BasicTxTest   : inner.isNewTransaction() = false

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);
        //2022-10-15 23:47:57.935 DEBUG 4292 --- [           main] o.s.j.d.DataSourceTransactionManager     : Initiating transaction commit
    }

    private void inner() {
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction() = {}", inner.isNewTransaction());
        log.info("내부 트랜잭션 커밋");

        // 하나의 트랜잭션으로 묶여서 커밋 코드가 있어도 커밋하지 않는다.
        // 내부 트랜잭션 코드는 실질적인 기능을 하는 것이 없다.
        txManager.commit(inner);
    }

    @Test
    void outer_rollback() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);

        log.info("외부 트랜잭션 롤백");
        txManager.rollback(outer);

        //2022-10-16 22:47:47.773  INFO 1560 --- [           main] hello.springtx.propagation.BasicTxTest   : 내부 트랜잭션 커밋
        //2022-10-16 22:47:47.773  INFO 1560 --- [           main] hello.springtx.propagation.BasicTxTest   : 외부 트랜잭션 롤백
        //2022-10-16 22:47:47.774 DEBUG 1560 --- [           main] o.s.j.d.DataSourceTransactionManager     : Initiating transaction rollback
        //2022-10-16 22:47:47.774 DEBUG 1560 --- [           main] o.s.j.d.DataSourceTransactionManager     : Rolling back JDBC transaction on Connection [HikariProxyConnection@888942941 wrapping conn0: url=jdbc:h2:mem:1da59437-74fe-4867-baa5-b24a809805f4 user=SA]
    }

    @Test
    void inner_rollback() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 커밋");
        txManager.rollback(inner); // rollback-only

        log.info("외부 트랜잭션 롤백");

        Assertions.assertThatThrownBy(() -> txManager.commit(outer))
                .isInstanceOf(UnexpectedRollbackException.class);

        // 내부 논리 트랜잭션이 하나라도 롤백되면 롤백 전용 마크를 표시한다.
        // 이후 커밋 요청이 들어오면 UnexpectedRollbackException 를 반환한다.
        // 롤백을 하되 해당 오류를 명확히 사용자나 개발자에게 반환해서 알려주어야한다.
    }

    @Test
    void inner_rollback_requires_new() {
        log.info("외부 트랜잭션 시작");
        // conn0 사용
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction());

        log.info("내부 트랜잭션 시작");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        // 항상 새로운 트랜잭션을 만드는 설정 : PROPAGATION_REQUIRES_NEW
        // conn1 사용
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("inner.isNewTransaction() = {}", inner.isNewTransaction());

        log.info("내부 트랜잭션 롤백");
        // conn1이 롤백되고, 풀은 반납된다.
        txManager.rollback(inner);

        log.info("외부 트랜잭션 커밋");
        // conn0은 트랜잭션이 살아있고 readOnly 설정을 확인하고, 커밋한다.
        txManager.commit(outer);

        // 내부 트랜잭션이 롤백되더라도 외부에서는 커밋처리된다.
    }
}
