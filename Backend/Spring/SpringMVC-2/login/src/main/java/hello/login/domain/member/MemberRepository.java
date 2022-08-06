package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save : member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    // Optional : java 8 에서 추가된 클래스
    // null 이 올 수 있는 값(Member)를 Optional 로 감싸주면 NullPointerException 을 방지
    public Optional<Member> findByLoginId(String loginId) {
//        List<Member> all = findAll();
//        for (Member m : all) {
//            // Member List 를 순회하며 사용자가 입력한 loginId 값과 일치시 값 반환
//            if (m.getLoginId().equals(loginId)) {
//                // 값이 null 이 아니라는 것을 확인하고 Optional 값을 생성(만약, 값을 생성시에 null 값이면 NPE 예외 발생)
//                return Optional.of(m);
//            }
//        }
        // 순회 결과 일치하는 값이 없다면 null 반환
        // return Optional.empty();

        // stream 으로 처리한 방식
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                // filter 결과 조건에 일치한 객체 m 을 발견시 즉시 해당 값을 반환
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
