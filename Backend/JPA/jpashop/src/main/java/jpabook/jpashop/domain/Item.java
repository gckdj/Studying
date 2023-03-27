package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
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