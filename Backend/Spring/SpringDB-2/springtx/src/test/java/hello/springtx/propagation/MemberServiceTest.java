package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /*
     * memberService     @Transaction : OFF
     * memberRepository  @Transaction : ON
     * logRepository     @Transaction : ON
     */
    @Test
    void outerTxOff_success() {
        // given
        String username = "outerTxOff_success";

        // when
        memberService.joinV1(username);

        // then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /*
     * memberService     @Transaction : OFF
     * memberRepository  @Transaction : ON
     * logRepository     @Transaction : ON Exception
     */
    @Test
    void outerTxOff_fail() {
        // given
        // 런타임 예외가 발생되도록 로그예외 문구 추가
        String username = "로그예외_outerTxOff_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        // then
        // 멤버는 커밋, 로그는 런타임 예외로 롤백처리
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /*
     * memberService     @Transaction : ON
     * memberRepository  @Transaction : OFF
     * logRepository     @Transaction : OFF
     */
    @Test
    void singleTx() {
        String username = "outerTxOff_fail";

        memberService.joinV1(username);

        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());

        // 서비스에서 트랜잭션을 걸면 하위에서 실행되는 로직은 모두 서비스에서 시작한 트랜잭션에 묶인다.
    }

    /*
     * memberService     @Transaction : ON
     * memberRepository  @Transaction : ON
     * logRepository     @Transaction : ON
     */
    @Test
    void outerTxOn_success() {
        String username = "outerTxOn_success";

        memberService.joinV1(username);

        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());

        // 서비스에서 트랜잭션을 걸면 하위에서 실행되는 로직은 모두 서비스에서 시작한 트랜잭션에 묶인다.
    }

    /*
     * memberService     @Transaction : ON
     * memberRepository  @Transaction : ON
     * logRepository     @Transaction : ON Exception
     */
    @Test
    void outerTxOn_fail() {
        String username = "로그예외_outerTxOn_fail";

        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        // 리포지토리에 존재하는 트랜잭션에 참여, 런타임예외 발생 후 참여하고 있는 트랜잭션에 리드온리 마킹
        // 2022-10-20 19:22:12.185 DEBUG 1295 --- [           main] cResourceLocalTransactionCoordinatorImpl : JDBC transaction marked for rollback-only (exception provided for stack trace)
        // 서비스에서는 넘어온 예외를 확인하고 물리 트랜잭션을 롤백
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /*
     * memberService     @Transaction : ON
     * memberRepository  @Transaction : ON
     * logRepository     @Transaction : ON(REQUIRES_NEW) Exception
     */
    @Test
    void recoverException_success() {
        String username = "로그예외_recoverException_success";

        memberService.joinV2(username);

        // 멤버 커밋, 로그 롤백
        // REQUIRES_NEW는 항상 새로운 트랜잭션을 생성.. 물리 트랜잭션이 분리되었음.
        // 로그 리포지토리에서 롤백이 되더라도 멤버는 그대로 커밋
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }
}