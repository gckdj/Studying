package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
// DB에서 인식을 못하는 경우가 있어 ORDERS로 명명(ORDER BY 충돌)
@Table(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    private Long id;

    private Long memberId;
    private LocalDateTime orderDate;

    // Ordinal시 추후 문제발생 가능성
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
