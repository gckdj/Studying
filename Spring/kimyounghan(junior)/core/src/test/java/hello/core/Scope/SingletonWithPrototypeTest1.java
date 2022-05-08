package hello.core.Scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

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
