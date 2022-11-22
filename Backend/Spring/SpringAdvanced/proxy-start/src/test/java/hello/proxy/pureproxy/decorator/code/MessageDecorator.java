package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        // 데코레이터 패턴은 주입받은 컴포넌트 객체를 대신 호출하고,
        // 원코드를 바꾸지 않고 반환 값을 꾸밀 수 있다.(데코레이터)
        String result = component.operation();
        String decoResult = "*****" + result + "*****";
        log.info("MessageDecorator 꾸미기 적용 전 = {}, 적용 후 = {}", result, decoResult);
        return decoResult;
    }
}
