package hello.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService service;

    @Test
    void runtimeException() {

        Assertions.assertThatThrownBy(() -> service.runtimeException())
                .isInstanceOf(RuntimeException.class);

        //로그 상으로 커밋, 롤백 여부 알수없음
        //2022-10-10 18:54:15.437 TRACE 5514 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.exception.RollbackTest$RollbackService.runtimeException]
        //2022-10-10 18:54:15.440  INFO 5514 --- [           main] h.s.e.RollbackTest$RollbackService       : call runtimeException
        //2022-10-10 18:54:15.440 TRACE 5514 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.exception.RollbackTest$RollbackService.runtimeException] after exception: java.lang.RuntimeException

        //프로퍼티 값 추가 후
        //2022-10-10 18:56:33.918 DEBUG 5629 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [hello.springtx.exception.RollbackTest$RollbackService.runtimeException]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        //2022-10-10 18:56:33.938 DEBUG 5629 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Opened new EntityManager [SessionImpl(91463800<open>)] for JPA transaction
        //2022-10-10 18:56:33.940 DEBUG 5629 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@55638165]
        //2022-10-10 18:56:33.940 TRACE 5629 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.exception.RollbackTest$RollbackService.runtimeException]
        //2022-10-10 18:56:33.943  INFO 5629 --- [           main] h.s.e.RollbackTest$RollbackService       : call runtimeException
        //2022-10-10 18:56:33.943 TRACE 5629 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.exception.RollbackTest$RollbackService.runtimeException] after exception: java.lang.RuntimeException
        //2022-10-10 18:56:33.943 DEBUG 5629 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction rollback
        //2022-10-10 18:56:33.943 DEBUG 5629 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Rolling back JPA transaction on EntityManager [SessionImpl(91463800<open>)]
        //2022-10-10 18:56:33.944 DEBUG 5629 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Closing JPA EntityManager [SessionImpl(91463800<open>)] after transaction
    }

    @Test
    void checkedException() {
        Assertions.assertThatThrownBy(() -> service.checkedException())
                .isInstanceOf(MyException.class);

        // 체크 예외는 커밋처리

        //2022-10-10 18:59:40.977 DEBUG 5777 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [hello.springtx.exception.RollbackTest$RollbackService.checkedException]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        //2022-10-10 18:59:40.999 DEBUG 5777 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Opened new EntityManager [SessionImpl(1430053704<open>)] for JPA transaction
        //2022-10-10 18:59:41.001 DEBUG 5777 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@a03529c]
        //2022-10-10 18:59:41.001 TRACE 5777 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.exception.RollbackTest$RollbackService.checkedException]
        //2022-10-10 18:59:41.004  INFO 5777 --- [           main] h.s.e.RollbackTest$RollbackService       : call checkedException
        //2022-10-10 18:59:41.004 TRACE 5777 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.exception.RollbackTest$RollbackService.checkedException] after exception: hello.springtx.exception.RollbackTest$MyException
        //2022-10-10 18:59:41.004 DEBUG 5777 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
        //2022-10-10 18:59:41.004 DEBUG 5777 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(1430053704<open>)]
        //2022-10-10 18:59:41.005 DEBUG 5777 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Closing JPA EntityManager [SessionImpl(1430053704<open>)] after transaction
    }

    @Test
    void rollbackFor() {
        Assertions.assertThatThrownBy(() -> service.rollbackFor())
                .isInstanceOf(MyException.class);
    }

    // 동일한 체크 예외이지만, rollbackFor가 설정되어 롤백처리

    //2022-10-10 19:01:00.210 DEBUG 5841 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [hello.springtx.exception.RollbackTest$RollbackService.rollbackFor]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-hello.springtx.exception.RollbackTest$MyException
    //2022-10-10 19:01:00.231 DEBUG 5841 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Opened new EntityManager [SessionImpl(96527084<open>)] for JPA transaction
    //2022-10-10 19:01:00.233 DEBUG 5841 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@4af70b83]
    //2022-10-10 19:01:00.233 TRACE 5841 --- [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [hello.springtx.exception.RollbackTest$RollbackService.rollbackFor]
    //2022-10-10 19:01:00.237  INFO 5841 --- [           main] h.s.e.RollbackTest$RollbackService       : call RollBackFor
    //2022-10-10 19:01:00.237 TRACE 5841 --- [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [hello.springtx.exception.RollbackTest$RollbackService.rollbackFor] after exception: hello.springtx.exception.RollbackTest$MyException
    //2022-10-10 19:01:00.237 DEBUG 5841 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction rollback
    //2022-10-10 19:01:00.237 DEBUG 5841 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Rolling back JPA transaction on EntityManager [SessionImpl(96527084<open>)]
    //2022-10-10 19:01:00.238 DEBUG 5841 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Closing JPA EntityManager [SessionImpl(96527084<open>)] after transaction


    @TestConfiguration
    static class RollbackTestConfig {

        @Bean
        RollbackService rollbackService() {
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService {

        // 런타임 예외 발생 : 롤백
        @Transactional
        public void runtimeException() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        // 체크 예외 발생 : 커밋
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }

        // 체크 예외 rollbackFor 지정 : 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException {
            log.info("call RollBackFor");
            throw new MyException();
        }
    }

    static class MyException extends Exception {

    }
}
