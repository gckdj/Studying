package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    // 부모요소 저장 시 자식요소 저장
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<Child> childList = new ArrayList<>();

    // 양방향매핑
    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String  name) {
        this.name = name;
    }
}
