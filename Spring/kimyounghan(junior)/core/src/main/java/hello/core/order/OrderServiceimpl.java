package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceimpl implements OrderService {

    // @Autowired private final MemberRepository memberRepository; 로 주입가능 (필드)
    // 코드는 간결하나, 코드를 중간에 변경하고 테스트하기 까다로움
    // 앱테스트 코딩에서는 편함
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    // Autowired 는 @Component 가 붙은 구현 클래스 찾아서 자동으로 값을 넣어준다.
    // 개발자는 새로운 구현 클래스를 만들더라도 내부로직은 건드리지 않고 @Component 만 바꿔서 달아주면 됨.
    // 지금처럼 생성자 메서드가 하나만 있으면 스프링 자체로 @Autowired 를 붙여줌
    // @Autowired(required = false) 로 지정시 주입할 대상이 없더라도 오류가 발생하지 않음.
    @Autowired
    public OrderServiceimpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 구현 클래스가 스프링빈으로 등록된 상태어야 @Autowired로 동작.
    // OrderService 클래스는 AppConfig 클래스에서 빈으로 등록되었음.
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}