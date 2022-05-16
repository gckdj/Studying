package hello.servlet.domain.member;

import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    // 순서가 보장이 되지않아 테스트 종료 후 메모리에 저장된 값을 비워주는 것
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // 메모리에 저장된 member 객체가 찾아지는지 확인
        Member member = new Member("hello", 20);

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        // member 객체들이 제대로 저장되어 있는지 갯수로 확인
        Member m1 = new Member("member1", 20);
        Member m2 = new Member("member2", 30);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(m1, m2);
    }
}
