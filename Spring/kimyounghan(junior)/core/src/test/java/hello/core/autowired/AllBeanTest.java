package hello.core.autowired;

import hello.core.discount.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;

public class AllBeanTest {

    @Test
    void findAllBean() {
        new AnnotationConfigApplicationContext();
    }

    static class DiscountService() {
        private final Map<String, DiscountPolicy> policyMap;
        private final list

    }

}
