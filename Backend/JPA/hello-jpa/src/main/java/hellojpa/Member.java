package hellojpa;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 1:N 관계시 N에는 @ManyToOne
    // 조인 시 매핑할 컬럼 @JoinColumn
    // @ManyToOne
    // @JoinColumn(name = "TEAM_ID")
    // insertable + updatable => false 조회전용 속성
    // 지연로딩 -> 객체 내 다른매핑 관계 프록시처리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    private Team team;

    // 임베디드 타입 사용 전후 매핑테이블은 동일
    // 좋은 설계 : 매핑 테이블 총 수 < 생성된 클래스의 수
    @Embedded
    private Period  workPeriod;

    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOODS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set <String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();

    /*@OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();*/

    // 객체 내 무한루프를 주의 like toString은 Team 같은 객체의 toString을 다시 호출한다.
    // 무한루프 즉시 스택오버플로우
    // lombok의 toString은 가급적 사용을 자제할 것, 생성하더라도 확인할 것
    // Entity를 컨트롤러의 결과값으로 제공하지말것..(엔티티가 변경될 경우 api 스펙이 변경되는것) -> dto로 변환하고 반환

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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
