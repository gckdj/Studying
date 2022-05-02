package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
/*@Qualifier("fixDiscountPolicy")*/
// 보편적으로 동일한 타입의 스프링 빈을 2개 이상 사용할때 Primary를 사용한다. 2개 이상일때 우선 호출
// Qualifier 같은 것들을 일일이 붙여주면서 코드가 지저분해짐. ,, 보조 DB 등을 사용하는 경우는 그렇게 많지않다.
// 우선순위 : Qualifier > Primary
@Primary
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP){
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}