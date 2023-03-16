package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// JPA 관리영역
// @Entity
// 테이블과 클래스명이 다르면
// @Table(name = User)
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ",
//        initialValue = 1,
//        allocationSize = 1
//)
// allocationSize => 시퀀스 사전확보, 생성된 이전의 시퀀스는 메모리에서 사용
// @TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class test_Member {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR"
    )
    private Long id;

    // 컬럼과 속성명이 다를경우 사용
    @Column(name = "name", nullable = false)
    private String username;

    private Integer age;

    // EnumType 기본값인 Ordinal 사용금지 (데이터베이스에 순서로 입력 ex: 0, 1)
    // 이넘의 순서가 변경되는 경우 데이터가 깨지게됨
    // 스트링타입으로 지정해줘서 명확한 이넘타입을 저장하는게 낫다 (ex: USER, GUEST..)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // @Temporal: 데이터 객체와 데이터베이스를 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // java 8 이후 LocalDate 클래스를 활용하면
    // Temporal 생략가능
    // 년과 월만 포함된 데이터
    private LocalDate testLocalData;

    // 시간까지 포함된 데이터
    private LocalDateTime testLocalDataTime;

    @Lob
    private String description;

    // JPA를 위한 기본 생성자
    public test_Member() {

    }

    public test_Member(Long id, String username, Integer age, RoleType roleType, Date createdDate, Date lastModifiedDate, String description) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.roleType = roleType;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", roleType=" + roleType +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", description='" + description + '\'' +
                '}';
    }
}
