package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderRepositoryV1;

public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositoryV2;

    public OrderServiceV2(OrderRepositoryV2 orderRepositoryV1) {
        this.orderRepositoryV2 = orderRepositoryV1;
    }

    public void orderItem(String itemId) {
        orderRepositoryV2.save(itemId);
    }
}
