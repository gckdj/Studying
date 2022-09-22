package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
// JPA와 객체를 매핑하는 어노테이션
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카멜 -> 언더스코어 자동변환 (생략가능)
    @Column(name = "item_name", length = 10)
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
