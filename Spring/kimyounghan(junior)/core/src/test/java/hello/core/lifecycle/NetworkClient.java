package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient /* implements InitializingBean, DisposableBean */ {
    
    private String url;
    
    public NetworkClient()  {
        connect();
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void connect() {
        System.out.println("connect: " + url);
    }
    
    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }
    
    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }
    /* 스프링 인터페이스에 의존하는 메서드 구현하는 방법 시작 */
    
    // InitailizingBean 구현 시 추가되는 메서드
    // 스프링 시작시 빈들이 등록되고 나면 실행되는 메서드
    // @Override
    // public void afterPropertiesSet() throws Exception {
    //     connect();
    //     call("초기화 연결 메시지");
    // }
    
    // DisposableBean 구현 시 추가되는 메서드
    // 스프링 종료 전에 실행되는 메서드
    // @Override
    // public void destroy() throws Exception {
    //     System.out.println("NetworkClient.destroy");
    //     disconnect();
    // }
    
    // 스프링 전용 인터페이스를 의존하고 있어, 초기화 또는 소멸 메서드의 이름을 변경할 수 없다.
    // 초창기에 나온 방법이고, 현재에는 거의 사용하지 않는다.
    
    /* 스프링 인터페이스에 의존하는 메서드 구현하는 방법 끝 */
    
    /* 빈에 메서드를 명시하는 방법 시작 */
    
    // public void init() {
    //     System.out.println("NetworkClient.init()");
    //     connect();
    //     call("초기화 연결 메시지");
    // }
    
    // public void close() throws Exception {
    //     System.out.println("NetworkClient.close()");
    //     disconnect();
    // }
    
    /* 빈에 메서드를 명시하는 방법 종료 */
    
    /* @PostContstruct, @PreDestroy 에노테이션을 이용하는 방법 시작 */
    
    // 자바진영에서 공식적으로 지원하는 에노테이션, 스프링이 아닌 다른 프레임워크, 컨테이너에서도 동작
    // 유일한 단점은 외부 라이브러리에는 적용할 수가 없다. 외부라이브러리를 적용할 경우에는 빈에 메서드(initMethod, destroyMethod)를 명시하는 방법을 이용
    
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init()");
        connect();
        call("초기화 연결 메시지");
    }
    
    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close()");
        disconnect();
    }
}