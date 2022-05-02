package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {
    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceimpl orderServiceimpl = new OrderServiceimpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order = orderServiceimpl.createOrder(1L, "itemA", 10000);
        // 정상적으로 주입되어, 사용자와 주문이 생성되어 사용자의 등급에 맞는 할인금액(=1000) 테스트 -> 통과
        // 스프링 없이 순수한 자바코드를 통해 테스트에서 필요한 구현체를 직접 조합 (장점)
        // 생성자를 통해 final 을 사용가능 -> final이 선언되면 최초 생성자 주입 이후 앱에서 값이 변동될 일 X
        // final 을 선언하면 생성자 주입 코딩을 누락해도 컴파일 단계에서 빨간불 찍어줌 -> 개발자의 실수 미연방지
        // `` 컴파일 단계의 오류가 세상에서 가장 좋다 ``
        // 수정자 주입은 옵션이 필요한 주입시에 활용하면 좋음 -> 대부분의 경우에는 생성자
        org.assertj.core.api.Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}