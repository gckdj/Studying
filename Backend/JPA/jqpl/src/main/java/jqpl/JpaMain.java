package jqpl;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from Member m left join m.team t";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result.size() = " + result.size());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    //// TypedQuery<String> q2 = em.createQuery("select m.username from Member m", String.class);
    //            // 타입정보를 받을 수 없을때
    //            // Query q3 = em.createQuery("select m.username, m.age from Member m");
    //
    //            TypedQuery<Member> q1 = em.createQuery("select m from Member m", Member.class);
    //            // 다중값(getResultList)
    //            // 싱글값(getSingleResult)은 결과가 반드시 하나여야 써야함. 둘 이상이면 예외발생
    //            List<Member> resultList = q1.getResultList();
    //
    //            for (Member member1 : resultList) {
    //                System.out.println("member1 = " + member1);
    //            }
    //
    //// createQuery: 조회데이터 영속객체 관리
    //            // 조회 이후 객체데이터 변경 시 -> db update
    //            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
    //            // 파라미터를 순서로 입력하는 방법도 있지만, 파라미터 순서가 바뀌면 다 바뀌기 때문에
    //            // 명칭에 매핑하는 방법이 좋다.
    //            query.setParameter("username", member.getUsername());
    //            Member singleResult = query.getSingleResult();
    //            System.out.println("singleResult = " + singleResult.getUsername());

    //// 여러 필드가 나열된 경우 배열객체로 반환한다.
    //            List resultList = em.createQuery("select m.username, m.age from Member m")
    //                    .getResultList();
    //
    //            // 엔티티가 아닌 정의된 클래스로 객체 반환가능
    //            em.createQuery("select new jqpl.MemberDTO(m.username, m.age) from Member m", MemberDTO.class);

    //// sql 방언들을 추상화해 페이징 api를 제공한다.
    //            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
    //                    .setFirstResult(0)
    //                    .setMaxResults(10)
    //                    .getResultList();
    //
    //            System.out.println("resultList.size() = " + resultList.size());
    //            for (Member member1 : resultList) {
    //                System.out.println("member1 = " + member1);
    //            }
}
