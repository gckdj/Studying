package hello.proxy.pureproxy.concreteLogic;

import hello.proxy.pureproxy.concreteLogic.code.ConcreteClient;
import hello.proxy.pureproxy.concreteLogic.code.ConcreteLogic;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        
    }
}
