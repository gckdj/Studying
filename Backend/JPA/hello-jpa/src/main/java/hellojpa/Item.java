package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
// InheritanceType.TABLE_PER_CLASS -> 각 테이블마다 중복데이터 허용(name, price)
// 데이터조회 시 상속된 모든테이블 조회결과 union -> 비효율
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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