package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/*
* 트랙잭션 - @Transaction AOP
* */

@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_3 {

    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;
    // private final DataSource dataSource;


    public MemberServiceV3_3(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        // 트랜잭션 템플릿에 트랜잭션 매니저로 생산한 객체를 주입해주면서 커밋 롤백이 매우 깔끔해짐.
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        // 템플릿 내의 콜백함수로 감싸서 실행하면 템플릿 내의 로직을 수행하고 커밋, 롤백한다.
        txTemplate.executeWithoutResult((status) -> {
            // 비즈니스 로직
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
    // 비즈니스 로직 외의 트랜잭션과 관련된 코드는 지속적으로 반복되고 있음
    // -> 스프링은 트랜잭션 템플릿으로 템플릿 콜백 패턴을 제공해주고 있다.
    // but 트랙잭션 템플릿으로 로직을 간편하게 남기긴했지만 오직 로직만을 남기지만은 못했다.
    // -> 트랜잭션 프록시 필요성
}
