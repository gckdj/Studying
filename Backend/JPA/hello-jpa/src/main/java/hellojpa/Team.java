package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
// Date 같은 공통속성은 상속매핑
public class Team extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    // 팀과 멤버는 다대일 양방향 관계로 설계
    @OneToMany
    // JoinColumn이 없을 시 조인테이블 방식으로 전환(중간 테이블 생성방식, 비권장)
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<>();

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
