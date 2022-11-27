package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        // 클래스 기반 프록시의 단점
        // 자바 기본 문법상 부모클래스로부터 상속받을때 기본생성자를 호출하게됨
        // 부모클래스에 기본생성자는 없고 파라미터 1개를 받는 생성자만 있는 상태
        // 따라서 문법 준수를 위해 생성자에 null을 넣고 생성자호출
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
