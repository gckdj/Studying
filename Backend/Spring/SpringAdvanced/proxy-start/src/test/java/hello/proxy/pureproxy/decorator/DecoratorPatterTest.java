package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DecoratorPatterTest {

    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatterClient client = new DecoratorPatterClient(realComponent);
        client.execute();
    }

    @Test
    void decorator1() {
        Component realComponent = new RealComponent();
        // MessageDecorator -> 주입된 원 컴포넌트 값을 꾸며서 값을 반환
        Component messageDecorator = new MessageDecorator(realComponent);
        // 꾸며진 컴포넌트 값이 result로 반환됨
        DecoratorPatterClient client = new DecoratorPatterClient(messageDecorator);

        client.execute();

        //로깅이나 실행시간 측정시 활용가능
        //23:48:04.952 [main] INFO hello.proxy.pureproxy.decorator.code.MessageDecorator - MessageDecorator 실행
        //23:48:04.954 [main] INFO hello.proxy.pureproxy.decorator.code.RealComponent - RealComponent 실행
        //23:48:04.958 [main] INFO hello.proxy.pureproxy.decorator.code.MessageDecorator - MessageDecorator 꾸미기 적용 전 = data, 적용 후 = *****data*****
        //23:48:04.960 [main] INFO hello.proxy.pureproxy.decorator.code.DecoratorPatterClient - result = *****data*****
    }

    @Test
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        // 로직 실행시간 로깅데코
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatterClient client = new DecoratorPatterClient(timeDecorator);

        client.execute();
    }
}