package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();

        //12:23:35.417 [main] INFO hello.jdbc.exception.basic.UnCheckedTest - 예외 처리, message = ex
        //hello.jdbc.exception.basic.UnCheckedTest$MyUnCheckedException: ex
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();

        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUnCheckedException.class);
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUnCheckedException extends RuntimeException {
        public MyUnCheckedException(String message) {
            super(message);
        }
    }

    /**
     * UnChecked 예외는
     * 예외를 잡거나, 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */

        public void callCatch() {
            try {
                repository.call();
            } catch(MyUnCheckedException e) {
                // 예외처리로직
                log.info("예외 처리, message = {}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 잡지 않아도 된다. 자연스럽게 상위로 넘어간다.
         * 체크 예외와 다르게 throws 예외 선언을 하지 않아도 된다.
         */
        public void callThrow() {
            repository.call();
        }
    }

    static class Repository {
        public void call() {
            throw new MyUnCheckedException("ex");
        }
    }

    // 실무트렌드는 체크예외는 지양하고, 언체크 예외 위주로 사용.
    // 단, 계좌이체 실패와 같은 중요한 비즈니즈 로직에서는 체크예외를 처리해주기도 함.

    // why ? SQL오류, 네트워크연결 오류 들은 서비스에서 애플리케이션 로직 수준에서 처리할 수가 없다.
    // 그래서 던진다.
}
