package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl();
        OrderControllerInterfaceProxy orderControllerInterfaceProxy = new OrderControllerInterfaceProxy(controllerImpl, logTrace);
        return new OrderControllerV1Impl(orderService());
    }

    private OrderServiceV1 orderService() {
        return new OrderServiceV1Impl(orderRepository());
    }

    private OrderRepositoryV1 orderRepository() {
        return new OrderRepositoryV1Impl();
    }
}
