package jpabook.jpashop.domain;

import javax.persistence.*;


/**
 * 엔티티설계시 인덱스나, 제약조건은 명시해주는 것이
 * 코드 가독성에 도움
 */
@Entity
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

//    @Column(name = "ORDER_ID")
//    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

//    @Column(name = "ITEM_ID")
//    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    // 대문자 or 소문자 -> 회사관례
    // 스프링부트 방식 -> order_price
    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
