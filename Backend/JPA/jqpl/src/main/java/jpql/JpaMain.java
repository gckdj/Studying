package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member m1 = new Member();
            m1.setUsername("관리자1");
            em.persist(m1);

            Member m2 = new Member();
            m2.setUsername("관리자1");
            em.persist(m2);

            em.flush();
            em.clear();

            // 하이버네이트는 함수호출 시 group_concat(m.username)과 같은 직관적인 사용이 가능하다.
            // String query = "select function('group_concat', m.username) from Member m";

            // m.username: 상태필드, 추가적 탐색제한
            // String query = "select m.username from Member m";

            // 클래스 내 컬렉션 접근시 묵시적 조인발생(inner join), 추가적 탐색가능
            // String query = "select m.team.name from Member m";

            // 컬렉션 값: 추가적 탐색제한, 컬렉션 사이즈 제공
            // String query = "select t.members.size from Team t";

            // 조인구문: 결과값 객체에서 추가 탐색 시 해당 객체에 대한 추가검색
            // fetch 조인: 첫 검색 시 쿼리에 조인을 포함하여 검색결과에 객체에 대한 데이터도 모두 포함된다.
            String query = "select m from Member m join fetch m.team";
            List<Collection> resultList = em.createQuery(query, Collection.class).getResultList();

            for (Collection collection : resultList) {
                System.out.println("collection = " + collection);
            }

            // 결론: 명시적 조인을 사용하자
            // select m.username from Team t join t.members m

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

    //Team team = new Team();
    //            team.setName("teamA");
    //            em.persist(team);
    //
    //            Member member = new Member();
    //            member.setUsername("member1");
    //            member.setAge(10);
    //            member.setType(MemberType.ADMIN);
    //            member.setTeam(team);
    //            em.persist(member);
    //
    //            em.flush();
    //            em.clear();
    //
    //            // String query = "select m from Member m left join m.team t";
    //            // String query = "select m.username, 'HELLO', true from Member m where m.type = jpql.MemberType.ADMIN";
    //
    //            // 하이버네이트는 ||도 지원
    //            // locate: 찾는 문자열 인덱스 반환
    //            // size(jpa지원): 컬렉션의 크기 반환
    //            String query = "select concat('a', 'b') from Member m ";
    //
    //            List<Object[]> list = em.createQuery(query).getResultList();
    //
    //            for (Object[] objects : list) {
    //                System.out.println("objects = " + objects[0]);
    //                System.out.println("objects = " + objects[1]);
    //                System.out.println("objects = " + objects[2]);
    //            }
}
