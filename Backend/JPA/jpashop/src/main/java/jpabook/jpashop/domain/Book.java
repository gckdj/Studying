package jpabook.jpashop.domain;

import jdk.jfr.Experimental;

import javax.persistence.Entity;

@Entity
public class Book extends Item {

    private String author;
    private String isbn;
}
