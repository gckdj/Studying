package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        client.execute();
        client.execute();
        client.execute();

        //실제객체는 값이 없는 경우 한번만 호출, 이외에는 캐시된 값을 활용
        //서비스 속도 up -> 클라이언트 입장에서는 프록시 객체가 주입된 것인지 실제객체가 주입된 것인지 알수없음
        //23:26:02.499 [main] INFO hello.proxy.pureproxy.proxy.code.CacheProxy - 프록시 호출
        //23:26:02.500 [main] INFO hello.proxy.pureproxy.proxy.code.RealSubject - 실제 객체 호출
        //23:26:03.500 [main] INFO hello.proxy.pureproxy.proxy.code.CacheProxy - 프록시 호출
        //23:26:03.500 [main] INFO hello.proxy.pureproxy.proxy.code.CacheProxy - 프록시 호출
    }
}
