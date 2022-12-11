package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void basicConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        // A는 빈으로 등록된다.
        // A a = applicationContext.getBean("beanA", A.class);

        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        // B는 빈으로 등록되지 않는다.
        // B로 등록된 빈이 없기 때문에 테스트통과
        // Assertions.assertThrows(NoSuchBeanDefinitionException.class, () ->
        //        applicationContext.getBean(B.class)
        // );

        // 빈후처리기로 클래스가 바뀌었기 때문에 등록된 A클래스 빈은 없다.
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () ->
                applicationContext.getBean(A.class)
        );
    }

    @Slf4j
    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        // 스프링 빈 후처리기에 의해 생성된 빈(beanA)을 인식하고 처리하는 과정을 거친다.
        @Bean
        public AToBPostProcessor helloPostProcessor() {
            // 후처리기에서 A클래스를 B클래스로 봐꿔 반환
            return new AToBPostProcessor();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);
            // A클래스가 들어오면 B클래스로 대체
            if (bean instanceof A) {
                return new B();
            } else {
                return bean;
            }
        }
    }
}
