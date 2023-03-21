package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            Team team = new Team();
//            team.setName("TeamA");
//            // team.getMembers().add(member);
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeam(team);
//            em.persist(member);
//
//            team.addMember(member);
//
//            em.flush();
//            em.clear();
//
//            // 연관관계의 주인에 매핑을 해줘야 값이 처리됨
//            // 양방향 관계에서는 양쪽에 값을 넣어주는 것이 맞다.
//            // why? flush + clear가 없으면 1차 캐시에서 값을 조회하려하고, 컬렉션에 값이 없게 된다.
//            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시에서 조회
//            List<Member> members = findTeam.getMembers();
//
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }
//
//            team.getMembers().add(member);

            // Member findMember = em.find(Member.class, member.getId());
            // Team findTeam = findMember.getTeam();
            // List<Member> members = findMember.getTeam().getMembers();

            // 양방향관계의 경우 외래키가 있는 곳에 mapped by 걸어준다. (Member)
            // 외래키가 있는 곳이 주인이라는 기준을 정해놓는다면 설계 시 연관관계 매핑이 어려움 점을 줄일 수 있다.

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            team.getMembers().add(member);

            em.persist(team);

            em.flush();
            em.clear();

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
}
