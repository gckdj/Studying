package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPolicy = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPolicy).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        // Component가 붙은 스프링빈을 등록함.
        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;

            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        // 아래 로직으로 findAllBean() 테스트 수행 시 오류가 뜸(기대값 1000, 실제 0 -> 메서드에서 0을 고정으로 반환)
//        public int discount(Member member, int price, String fixDiscountPolicy) {
//            return 0;
//        }

        // DiscountPolicy 클래스에서 생성한 빈들을 Map 형태로 보관하고,
        // 빈의 이름인 문자열 discountCode를 Map의 Key로 활용, 빈을 꺼내 쓸 수 있다.
        // 매개변수를 문자열로 코딩하는 경우 실수가 많이 생길거같은데..
        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }

        // @Configuration을 붙인 설정 클래스에서 일일이 @Bean을 붙여 지정하는 것은
        // 프로젝트가 진행되며 빈이 많이 생성되면 상당한 노력을 요구한다.
        // 자동 빈을 사용해도 OCP, DIP를 지킬 수 있음.
        // 업무로직은 자동기능을 활용하는 것이 좋고, 기술 지원 로직은 수동 빈으로 등록하고,
        // 명확하게 밖으로 드러내는것이 좋음. 애플리케이션 전반적으로 영향을 끼치는 로직(기술지원로직)
        // 스프링 빈으로 등록된 객체들이 어떤 역할을 하는 것인지 효과적으로 알기 위해서도
        // 수동 등록이 좋다. (-> * 협업과 관련된 사항 * )

    }

}
