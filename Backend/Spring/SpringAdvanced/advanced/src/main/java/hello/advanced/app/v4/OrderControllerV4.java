package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그트레이스에 템플릿패턴을 적용
// 공통된 로직은 템플릿 익명 추상클래스에 두고,
// 각 비즈니스 단계에 맞는 부분에 call 메서드를 바꿔가며 로그트레이싱
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId) {
        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };
        return template.execute("OrderController.request()");
    }
}

// 부모클래스에서 상속받은 자식클래스이지만
// 상속의 장점은 활용못하고 단점만 발현
// -> 로직 내에 부모클래스에서 상속받았지만 메서드를 오버라이드하는것 말고 상속받은 메서드를 사용하는 것이 없음
// -> 그럼에도 자식클래스는 부모클래스의 추상메서드에 의존해 구현해야함
// * 전략패턴의 필요성 *