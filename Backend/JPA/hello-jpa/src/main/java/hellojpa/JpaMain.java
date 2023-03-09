package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // persistence.xml내 정보활용
        // emf는 앱로딩 시점에 단 한번만 진행
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 로직작성
        // JPA 모든 로직 트랜잭션 내에서 진행
        try {
            /*
            Member member = new Member();
            member.setId(2L);
            member.setName("B");
            */

            /*Member findMember = em.find(Member.class, 1L);
            System.out.println("find Member = " + findMember);
            findMember.setName("helloJpa");*/

            // 객체수정 후 commit => database update
            //    /* update
            //        hellojpa.Member */ update
            //            Member
            //        set
            //            name=?
            //        where
            //            id=?

            // em.persist(member);

            // JPQL
            // Member 객체대상 쿼리(테이블쿼리 X)
            /*List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }*/

            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            // 영속데이터 -> 캐싱 -> JPA 1순위 : 캐싱데이터, 2순위 : DB -> select 쿼리 X
            // 현업에서 성능적인 이점을 얻기는 어렵지만, 컨셉적으로 이점을 얻을 수 있다.
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            // 영속데이터 O -> 캐싱된 데이터사용
            Member anotherFindMember = em.find(Member.class, 101L);

            // result = true
            System.out.println("result = " + (findMember == anotherFindMember));

            // 영속시점 -> 실제 데이터베이스 영향 X
            // 커밋시점 -> 영향 O
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            // em => 각 쓰레드별 생성 후 제거(서비스 장애원인)
            // 데이터관리 => 항상 트랜잭션 내 관리
            em.close();
        }

        emf.close();
        //Hibernate:
        //    /* insert hellojpa.Member
        //        */ insert
        //        into
        //            Member
        //            (name, id)
        //        values
        //            (?, ?)
        // => persistence.xml options
    }
}
