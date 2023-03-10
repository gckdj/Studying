package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// JPA 관리영역
@Entity
// 테이블과 클래스명이 다르면
// @Table(name = User)
public class Member {

    @Id
    private Long id;

    // JPA를 위한 기본 생성자
    public Member() {

    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // 컬럼명과 속성값이 다르면
    // @Column(name = username)
    private String name;

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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
