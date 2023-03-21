package hellojpa;

import javax.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 1:N 관계시 N에는 @ManyToOne
    // 조인 시 매핑할 컬럼 @JoinColumn
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    // 객체 내 무한루프를 주의 like toString은 Team 같은 객체의 toString을 다시 호출한다.
    // 무한루프 즉시 스택오버플로우
    // lombok의 toString은 가급적 사용을 자제할 것, 생성하더라도 확인할 것
    // Entity를 컨트롤러의 결과값으로 제공하지말것..(엔티티가 변경될 경우 api 스펙이 변경되는것) -> dto로 변환하고 반환
}
