package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember = " + findMember);
            findMember.setName("helloJpa");

            // 객체수정 후 commit => database update
            //    /* update
            //        hellojpa.Member */ update
            //            Member
            //        set
            //            name=?
            //        where
            //            id=?

            // em.persist(member);
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
