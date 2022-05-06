package hello.core.lifecycle;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BenaLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }
    
    @Configuration
    static class LifeCycleConfig {
        
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-sprint.dev");
            return networkClient;
        }
    }
    
    // 테스트 결과 의존관계 주입이 완료되기 이전에 호출하는 경우가 발생하고 url 변수가 null로 출력
    // 스프링은 의존관계가 주입되면 스프링 빈에 '콜백메서드'를 통해서 초기화 시점을 알려주는 다양한 기능을 제공
    // 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 줌으로써 안전하게 종료작업 진행
    // 객체의 생성과 초기화는 분리하는 것이 좋다. (-> 생성자 내에서는 객체 내에서의 값을 지정하는 정도로 하는 것이 좋다.)
    
}
