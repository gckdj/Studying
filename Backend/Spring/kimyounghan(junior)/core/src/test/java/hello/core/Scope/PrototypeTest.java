package hello.core.Scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

// 프로토타입 빈
public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonTest.SingletonBean.class);
        System.out.println("find pb1");
        PrototypeBean pb1 = new PrototypeBean();
        System.out.println("find pb2");
        PrototypeBean pb2 = new PrototypeBean();

        System.out.println("pb1 = " + pb1);
        System.out.println("pb2 = " + pb2);

//      pb1 = hello.core.Scope.PrototypeTest$PrototypeBean@27a5328c
//      pb2 = hello.core.Scope.PrototypeTest$PrototypeBean@1e5f4170
//      BUILD SUCCESSFUL in 3s
        assertThat(pb1).isNotSameAs(pb2);

        pb1.destroy();
        pb2.destroy();

        ac.close();
    }

    // @Scope를 붙이면 @Component 처럼 빈으로 스캔된다.
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("Prototype.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Prototype.destroy");
        }
    }

    // 초기화 시점 : 싱글턴 -> 스프링 컨테이너 생성시, 프로토타입 -> 스프링 컨테이너에서 빈을 조회시 생성 및 초기화
    // 싱글턴 객체로 관리시 스프링 컨테이너 종료시에 자동으로 빈 객체를 소멸시키는데
    // 프로토타입 객체는 스프링에서 생성 및 초기화만 관여하고 소멸은 관여하지 않는다 -> destroy 메서드로 관리필요
}
