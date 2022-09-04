package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    public void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야 한다.
     */
    static class Service {

        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public  void callCatch() {
            try {
                repository.call();
                // MyCheckedException을 Exception으로 변경해도 최상위 예외인 Exception은 해당 예외와 그 하위타입을
                // 모두 잡아주기 때문에 정상적으로 예외처리된다.
            } catch (MyCheckedException e) {
                log.info("예외처리, message = {}", e.getMessage(), e);
            }
        }
        //12:08:29.149 [main] INFO hello.jdbc.exception.basic.CheckedTest - 예외처리, message = ex
        //hello.jdbc.exception.basic.CheckedTest$MyCheckedException: ex

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 반드시 메서드에 필수로 선언해야한다.
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        // 예외를 던지는 경우 메서드에 명시되어야함. -> 컴파일러 인식
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }
}
