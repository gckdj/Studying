package hello.core.Scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

// 싱글턴 빈
public class SingletonTest {

    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        // 싱글턴 패턴 확인
        SingletonBean s1 = ac.getBean(SingletonBean.class);
        SingletonBean s2 = ac.getBean(SingletonBean.class);

        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

//      s1 = hello.core.Scope.SingletonTest$SingletonBean@1568159
//      s2 = hello.core.Scope.SingletonTest$SingletonBean@1568159
//      BUILD SUCCESSFUL in 6s
        assertThat(s1).isSameAs(s2);
    }

    @Scope("singleton")
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
