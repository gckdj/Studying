package hello.jdbc.service;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

/**
 * 기본 동작, 트랜잭션이 없어서 문제발생
 */
class MemberServiceV1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberEx";
    // 이름이 ex인 경우 예외가 터지도록 예외설정되어있다.
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    // 테스트 종료이후 AfterEach가 호출
    @AfterEach
    void after() throws SQLException {
        // DB상의 데이터를 지워버리는 것보다 트랜잭션을 활용해서 테스트 종료 이후에 롤백시키는 것이 더욱 좋은 방법
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test
    @DisplayName("정상 이체 / 이체 중 예외발생")
    void accountTransfer() throws SQLException {

        // given
        Member memberA = new Member(MEMBER_A, 10000);
        // Member memberEx = new Member(MEMBER_B, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        // when
        // 예외발생하는지 테스트
        assertThatThrownBy(() -> memberService.accountTransfer(
                memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class); // 테스트 통과 -> 수동커밋으로 관리되고 있지 않아서 중간에 예외가 터지면 실행된 쿼리를 복구하지 않았다.
        // memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000);

        // then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findmemberEx = memberRepository.findById(memberEx.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        // assertThat(findmemberEx.getMoney()).isEqualTo(12000);
        assertThat(findmemberEx.getMoney()).isEqualTo(10000);
    }
}