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
            m2.setUsername("관리자2");
            em.persist(m2);

            Member m3 = new Member();
            m3.setUsername("관리자3");
            em.persist(m3);

            em.flush();
            em.clear();

            // update 구문실행 이후 영향받은 row 반환
            // 벌크연산 전에 flush 처리됨
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            // 현 상태에서 멤버객체의 데이터를 조회하면 기존과 값이 똑같다 (영속성 컨텍스트에는 미반영상태임)
            em.clear();

            // 영속성 컨텍스트를 지우고 디비에서 새로 조회해오면 반영되어있다.
            Member findMember = em.find(Member.class, m3.getId());

            System.out.println("findMember.getAge() = " + findMember.getAge());
            System.out.println("resultCount = " + resultCount);

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

    //// 하이버네이트는 함수호출 시 group_concat(m.username)과 같은 직관적인 사용이 가능하다.
    //            // String query = "select function('group_concat', m.username) from Member m";
    //
    //            // m.username: 상태필드, 추가적 탐색제한
    //            // String query = "select m.username from Member m";
    //
    //            // 클래스 내 컬렉션 접근시 묵시적 조인발생(inner join), 추가적 탐색가능
    //            // String query = "select m.team.name from Member m";
    //
    //            // 컬렉션 값: 추가적 탐색제한, 컬렉션 사이즈 제공
    //            // String query = "select t.members.size from Team t";
    //
    //            // 조인구문: 결과값 객체에서 추가 탐색 시 해당 객체에 대한 추가검색
    //            // fetch 조인: 첫 검색 시 쿼리에 조인을 포함하여 검색결과에 객체에 대한 데이터도 모두 포함된다.
    //            // String query = "select m from Member m join fetch m.team";
    //            // List<Collection> resultList = em.createQuery(query, Collection.class).getResultList();
    //
    //            // 결론: 명시적 조인을 사용하자
    //            // select m.username from Team t join t.members m
    //
    //            // String query = "select t from Team t join fetch t.members where t.name = '팀A'";
    //            List<Team> list = em.createQuery(query, Team.class).getResultList();
    //
    //            // 조인으로 인한 데이터 뻥튀기 주의
    //            // distinct 사용하는 경우 데이터베이스에서 처리가 안되어도 jpa에서 처리해준다.
    //            for (Team team : list) {
    //                System.out.println("team.getName() = " + team.getName() + "|team.getSize() = " + team.getMembers().size());
    //            }
    //
    //            // 페치조인 대상에 별칭을 주고 조인조건을 걸지말자
    //            // 페치조인은 데이터를 한번에 가져오는 장점이 있지만, 컬렉션을 조인하는 경우 데이터 뻥튀기 위험성이 있다.
    //            // 이러한 이유로 컬렉션을 페치조인하면 해당 데이터에 페이징을 제공하지 않는다.
    //            String query = "select t from Team t join fetch t.members as m where m.age > 10";

    //// 네임드쿼리는 해당 엔티티에 사전에 정의해둔  JPQL을 끌어쓰는 기능
    //            // 앱로딩 시점에 쿼리를 검증해주는 장점이 있음.
    //            List<Member> list = em.createNamedQuery("Member.findByUsername", Member.class)
    //                    .setParameter("username", "관리자1")
    //                    .getResultList();
    //
    //            for (Member member : list) {
    //                System.out.println("member = " + member);
    //            }
}
