package hello.itemservice.domain.item;

import lombok.Data;

// Getter, Setter와 다른 기능까지 다 만들어주는 롬복 어노테이션이나, 예상치 못하게 작동할 수 있어 사용권장 X
@Data
public class Item {

    private Long id;
    private String itemName;
    // int가 아닌 Integer는 null 값이 포함될 수 있음.
    private Integer price;
    private Integer quantity;

    public Item() {}

    public Item(Long id, String itemName, Integer price, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
