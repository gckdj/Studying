package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

// 단일테이블 전략에 의해 상속된 객체의 모든 속성이 단일테이블에 통합된다.
//create table Item (
//       DTYPE varchar(31) not null,
//        id bigint not null,
//        name varchar(255),
//        price integer not null,
//        actor varchar(255),
//        director varchar(255),
//        author varchar(255),
//        isbn varchar(255),
//        artist varchar(255),
//        primary key (id)
//    )