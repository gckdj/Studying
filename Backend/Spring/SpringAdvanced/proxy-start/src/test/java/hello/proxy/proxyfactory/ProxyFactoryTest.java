package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();

        // 비즈니스 인스턴스 주입, 프록시 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 프록시에 비즈니스 인스턴스 외 클래스 주입
        proxyFactory.addAdvice(new TimeAdvice());


        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        // 프록시 인스턴스에서 구현해놓은 로직실행
        proxy.save();
    }
}


