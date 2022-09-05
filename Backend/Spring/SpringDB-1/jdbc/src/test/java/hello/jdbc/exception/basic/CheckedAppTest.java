package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

public class CheckedAppTest {

    @Test
    void checked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                // Exception의 자손도 모두 통과
                .isInstanceOf(Exception.class);
    }

    static class Controller {
        Service service = new Service();

        public void request() throws SQLException, ConnectException{
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws SQLException, ConnectException {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }

    // SQLException을 던지는 경우 JDBC에 의존성이 생기게됨.
    // JDBC가 아닌 다른 리포지토리를 사용하는 경우 관련 예외를 모두 수정해야됨.
    // 최상위 예외인 Exception을 던지면 의존성 문제는 해결되지만 모든 예외사항을 던지게되어, 체크가 불가능해짐. 안티패
    // 언체크 예외 활용 필요성 UP

}
