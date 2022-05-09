package hello.core.Scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    // Provider 사용
    // PrototypeBean.init hello.core.Scope.SingletonWithPrototypeTest1$PrototypeBean@f415a95
    // PrototypeBean.init hello.core.Scope.SingletonWithPrototypeTest1$PrototypeBean@b93aad
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean pb1 = new PrototypeBean();
        PrototypeBean pb2 = new PrototypeBean();

        pb1.addCount();
        assertThat(pb1.getCount()).isEqualTo(1);

//      싱글턴으로 관리되고 있지 않기 때문에 count가 누적되지 않음.
        pb2.addCount();
        assertThat(pb2.getCount()).isNotEqualTo(2);
    }


    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        // 프로토타입 클래스로 생성해도 싱글턴처럼 작동
        ClientBean cb1 = ac.getBean(ClientBean.class);
        int cnt1 = cb1.logic();
        assertThat(cnt1).isEqualTo(1);

        ClientBean cb2 = ac.getBean(ClientBean.class);
        int cnt2 = cb2.logic();
        assertThat(cnt2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        // 생성시점 주입
        // private final PrototypeBean prototypeBean;

        // ApplicationContext에서 빈을 가져와도 되지만 안좋은 코드
        // @Autowired
        // ApplicationContext applicationContext;

        // @Autowired
        // public ClientBean(PrototypeBean prototypeBean) {
        // this.prototypeBean = prototypeBean;
        // }

        // public int logic() {
            // 메서드 호출마다 getBean() 하면 새로 생성됨
            // 필요한 의존관계를 주입받는 것이 아니라 조회(탐색)하고 있다.
            // 스프링 컨테이너에 종속적인 코드가 되고, 단위테스트가 어려워짐.
        //    PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
        //    int count = prototypeBean.getCount();
        //    return count;
        // }

        // getObject() 호출하면 스프링 컨테이너에서 찾아줌
        // 예전에는 ObjectFactory<PrototypeBean> 사용 → Provider에서는 getObject() 외 추가버전
        // 단위테스트를 만들거나 mock 코드 만들기가 쉬워짐
        // ObjectFactory : 단순한 기능, 스프링에 의존
        // ObjectProvider : ObjectFactory상속, 옵션 및 스트림 처리 등 편의기능이 많고 별도 라이브러리 불필요, 스프링에 의존
        // 스프링에 의존하지 않는 기능?
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            prototypeBean.addCount();
//            int cnt = prototypeBean.getCount();
//
//            return cnt;
//        }

        // JSR-330 자바표준기술을 사용하는 방법

        @Autowired
        // javax.inject.Provider
        // PrototypeBean.init hello.core.Scope.SingletonWithPrototypeTest1$PrototypeBean@62417a16
        // PrototypeBean.init hello.core.Scope.SingletonWithPrototypeTest1$PrototypeBean@525d79f0
        // 자바표준이므로 다른 컨테이너에서도 사용가능
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int cnt = prototypeBean.getCount();

            return cnt;
        }
    }

    @Scope("singleton")
    static class ClientBean2 {
        // 주입 받을때마다 새로생성하는 것이지, 사용할때마다 생성되지 않음
        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean2(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        // @PostConstruct, @PreDestroy -> 빈 타입이 싱글턴일때만 적용
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy " + this);
        }
    }
}
