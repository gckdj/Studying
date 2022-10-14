package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
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
}
