package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(new Address("city", "street", "10000"));
            member.setWorkPeriod(new Period());

            em.persist(member);

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
}
