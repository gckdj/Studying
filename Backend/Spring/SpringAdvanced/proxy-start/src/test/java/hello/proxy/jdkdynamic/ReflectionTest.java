package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);
        // 공통로직1 종료

        // 공통로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
        // 공통로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // 생성된 인스턴스에서 직접 메서드를 호출하지 않고 Method 클래스로 추상화
        // 각 클래스에 정의된 메서드를 동적으로 호출하기 때문에 반복적인 코드로 추상화 가능
        // but, 리플렉션 코드는 컴파일 오류에서 확인할 수 없고(호출할 메서드명을 문자열로 입력)
        // 런타임오류만 확인가능
        // 프레임워크나 매우 일반적인 공통처리가 필요한 로직에서만 사용

        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("call A");
            return "A";
        }

        public String callB() {
            log.info("call B");
            return "B";
        }
    }
}
