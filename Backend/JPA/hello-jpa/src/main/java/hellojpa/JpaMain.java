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
            /*Member member = new Member();
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
            System.out.println("result = " + (findMember == anotherFindMember));*/

            // 영속
            /*Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            // 영속성 컨텍스트에 저장
            // batch_size -> 쿼리를 날리기 이전 모으는 기준점 설정 -> JPA를 잘 활용하면 성능적 이점을 가질 수 있다.
            em.persist(member1);
            em.persist(member2);

            System.out.println("===== 쿼리 기준점 =====");
            */
            // 영속시점 -> 실제 데이터베이스 영향 X
            // 커밋시점 -> 영향 O

            /*Member member = em.find(Member.class, 150L);
            member.setName("zzzz");*/
            // JPA 영속성 컨텍스트는 객체의 값이 변경되면 감지 및 자체 업데이트 처리

            // flush 영속성 컨텍스트와 데이터베이스 동기화(영속성 컨텍스트가 초기화되는 것은 아님)
            /*Member member = new Member(200L, "member200");
            em.persist(member);
            em.flush();*/

            // Member member = new Member(200L, "member200");
            // member.setName("TESTTEST");

            // 영속성 컨텍스트에서 객체제외(커밋시 제외)
            // em.detach(member);
            // 컨텍스트 초기화
            // em.clear();

            /*Member member = new Member();
            member.setId(1L);
            member.setUsername("A");
            member.setRoleType(RoleType.USER);

            em.persist(member);out*/

            Member member = new Member();
            member.setUsername("C");
            System.out.println("===== 쿼리 기준점 =====");

            em.persist(member);
            System.out.println("member.id = " + member.getId());
            System.out.println("===== 쿼리 기준점 =====");

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
