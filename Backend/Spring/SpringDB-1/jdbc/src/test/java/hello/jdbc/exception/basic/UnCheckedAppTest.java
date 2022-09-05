package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void unchecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch(SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }

        public void runSQL() throws  SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
            //Caused by: java.sql.SQLException: ex
            //	at hello.jdbc.exception.basic.UnCheckedAppTest$Repository.runSQL(UnCheckedAppTest.java:54)
            //	at hello.jdbc.exception.basic.UnCheckedAppTest$Repository.call(UnCheckedAppTest.java:47)
            //	... 72 common frames omitted
        }
    }

    // 서비스에서 발생하는 오류 대부분은 복구가 불가능함. 런타임 예외는 서비스나 컨트롤러가 복구불가능한 예외를 신경쓰지 않아도 되고
    // 결과적으로 체크 예외처럼 강제로 예외를 의존하지 않아도 된다.

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex", e); // 두번째 파라미터에 예외로 넣어주면 예외가 로그로 찍히게 된다. (e.printStackTrace는 시스템계열)
        }
    }
}
