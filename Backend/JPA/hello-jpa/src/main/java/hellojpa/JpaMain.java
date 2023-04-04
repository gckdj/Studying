package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member m1 = new Member();
            m1.setUsername("member1");
            m1.setHomeAddress(new Address("city1", "street", "10000"));

            m1.getFavoriteFoods().add("치킨");
            m1.getFavoriteFoods().add("피자");
            m1.getFavoriteFoods().add("족발");

            m1.getAddressHistory().add(new Address("old1", "oldstreet1", "10001"));
            m1.getAddressHistory().add(new Address("old2", "oldstreet2", "10002"));

            em.persist(m1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, m1.getId());
            System.out.println(findMember.toString());

            List<Address> addressHistory = findMember.getAddressHistory();

            // select 쿼리가 따로 나간다(지연로딩 정책)
            /*for (Address address : addressHistory) {
                System.out.println("address = " + address);
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFoods = " + favoriteFoods);
            }*/

            // findMember.getAddressHistory().setCity("newCity"); (x) 어떤 사이드 이펙트가 발생할지 모른다
            // 바뀐부분만 수정해서 새로운 객체를 생성 삽입한다.
            // JPA는 remove 실행 시 키와 일치하는 모든 데이터를 삭제하고,
            // 기존 조회된 데이터와 신규생성할 데이터 쿼리를 날린다.
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newcity", a.getStreet(), a.getZipcode()));

            // 리스트 내용수정은 기존의 값을 삭제하고 신규값을 삽입
            // ex) 치킨 -> 한식
            // 리스트가 관리된 내용은 JPA가 처리한다.
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 컬렉션은 equals를 기본 호출한다.
            // 클래스 내에 오버라이드된 equals 메서드가 정확해야한다.
            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
            findMember.getAddressHistory().add(new Address("newcity1", "street", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    //Hibernate:
    //
    //    create table Member (
    //       member_id bigint not null,
    //        TEAM_ID bigint,
    //        USERNAME varchar(255),
    //        primary key (member_id)
    //    )
    //Hibernate:
    //
    //    create table Team (
    //       TEAM_ID bigint not null,
    //        name varchar(255),
    //        primary key (TEAM_ID)
    //    )

    ////            Team team = new Team();
    ////            team.setName("TeamA");
    ////            // team.getMembers().add(member);
    ////            em.persist(team);
    ////
    ////            Member member = new Member();
    ////            member.setUsername("member1");
    ////            member.setTeam(team);
    ////            em.persist(member);
    ////
    ////            team.addMember(member);
    ////
    ////            em.flush();
    ////            em.clear();
    ////
    ////            // 연관관계의 주인에 매핑을 해줘야 값이 처리됨
    ////            // 양방향 관계에서는 양쪽에 값을 넣어주는 것이 맞다.
    ////            // why? flush + clear가 없으면 1차 캐시에서 값을 조회하려하고, 컬렉션에 값이 없게 된다.
    ////            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시에서 조회
    ////            List<Member> members = findTeam.getMembers();
    ////
    ////            for (Member m : members) {
    ////                System.out.println("m = " + m.getUsername());
    ////            }
    ////
    ////            team.getMembers().add(member);

    //
    //            // Member findMember = em.find(Member.class, member.getId());
    //            // Team findTeam = findMember.getTeam();
    //            // List<Member> members = findMember.getTeam().getMembers();
    //
    //            // 양방향관계의 경우 외래키가 있는 곳에 mapped by 걸어준다. (Member)
    //            // 외래키가 있는 곳이 주인이라는 기준을 정해놓는다면 설계 시 연관관계 매핑이 어려움 점을 줄일 수 있다.
    //
    //           /* Movie movie = new Movie();
    //            movie.setDirector("aaaa");
    //            movie.setActor("bbbb");
    //            movie.setName("바람과 함께 사라지다");
    //            movie.setPrice(10000);
    //
    //            em.persist(movie);
    //
    //            em.flush();
    //            em.clear();
    //
    //            Movie findMovie = em.find(Movie.class, movie.getId());
    //            System.out.println("findMovie = " + findMovie.getName());*/
    //
    //            /*Member member = new Member();
    //            member.setUsername("user1");
    //            member.setCreatedBy("kim");
    //            member.setCreatedDate(LocalDateTime.now());*/
    //
    //            /*Member member = new Member();
    //            member.setUsername("hello");
    //
    //            em.persist(member);*/
    //
    //            // Member findMember = em.find(Member.class, member.getId());
    //
    //            // 가짜객체
    //            // 객체사용 시에 쿼리전송
    //            /*Member refMember = em.getReference(Member.class, member.getId());
    //            System.out.println("refMember.getClass() = " + refMember.getClass());
    //            System.out.println("refMember.getId() = " + refMember.getId());
    //            System.out.println("refMember.getUsername() = " + refMember.getUsername());*/
    //
    //            /*Member member1 = new Member();
    //            member1.setUsername("member1");
    //            em.persist(member1);
    //
    //            Member member2 = new Member();
    //            member2.setUsername("member2");
    //            em.persist(member2);
    //
    //            em.flush();
    //            em.clear();
    //
    //            Member m1 = em.find(Member.class, member1.getId());
    //            Member m2 = em.getReference(Member.class, member2.getId());
    //
    //            // find 결과비교 -> true
    //            // reference 결과비교 -> false
    //            // 프록시객체 고려 instanceof 사용할 것.
    //            System.out.println("m1 == m2 " + (m1 instanceof Member));
    //            System.out.println("m1 == m2 " + (m2 instanceof Member));*/

    //            Member member1 = new Member();
    //            member1.setUsername("member1");
    //            em.persist(member1);
    //
    //            em.flush();
    //            em.clear();
    //
    //            Member m1 = em.find(Member.class, member1.getId());
    //            System.out.println("m1 = " + m1.getClass());
    //
    //            Member ref = em.getReference(Member.class, member1.getId());
    //            System.out.println("ref = " + ref.getClass());
    //
    //            // 영속성 컨텍스트 내 객체가 있는 경우 -> JPA에서 true로 관리
    //            // 프록시로 있는 경우 -> 프록시 가져옴
    //            System.out.println("a == a : " + (m1 == ref));
    //
    //            // close or detach 사용, 영속성컨텍스트에 존재하지 않으면 proxy 예외발생
    //            // PersistenceUnitUtil.isLoaded() -> 객체로드 여부판별
    //            // Hibernate.initialize -> 프록시 강제초기화

    //            Team t1 = new Team();
    //            t1.setName("teamA");
    //            em.persist(t1);
    //
    //            Team t2 = new Team();
    //            t2.setName("teamB");
    //            em.persist(t2);
    //
    //            Member m1 = new Member();
    //            m1.setUsername("member1");
    //            m1.setTeam(t1);
    //            em.persist(m1);
    //
    //            Member m2 = new Member();
    //            m2.setUsername("member2");
    //            m2.setTeam(t2);
    //            em.persist(m2);
    //
    //            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
    //                    .getResultList();

    //Child c1 = new Child();
    //            Child c2 = new Child();
    //
    //            Parent parent = new Parent();
    //            parent.addChild(c1);
    //            parent.addChild(c2);
    //
    //            em.persist(parent);

    //Member member = new Member();
    //            member.setUsername("hello");
    //            member.setHomeAddress(new Address("city", "street", "10000"));
    //            member.setWorkPeriod(new Period());

    //Address address = new Address("city", "street", "10000");
    //
    //            Member m1 = new Member();
    //            m1.setUsername("member1");
    //            m1.setHomeAddress(address);
    //            em.persist(m1);
    //
    //            // address의 값이 변경되면 적용된 모든 객체의 값이 변한다. (객체데이터는 참조)
    //            // -> 인스턴스 복사활용 or 불편객체 사용 (Integer, String) or 객체 setter 제거
    //            Address copyAddr = new Address(address.getCity(), address.getStreet(), address.getZipcode());
    //
    //            Member m2 = new Member();
    //            m2.setUsername("member1");
    //            m2.setHomeAddress(copyAddr);
    //            em.persist(m2);
}
