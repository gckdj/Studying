package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager = 항상 새로운 커넥션을 획득
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // repository = new MemberRepositoryV1(dataSource);

        // 커넥션 풀링: HikariProxyConnection -> JdbcConnection
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        repository = new MemberRepositoryV1(dataSource);

        // 기본 DriverManager 클래스를 사용하면 항상 새로운 커넥션 풀이 생성되어 사용된다.
        // 반면, 히카리풀은 생성된 커넥션을 보관하고 계속 사용함.
        // DriverManager -> Hikari로 넘어간다하더라도 DataSource 인터페이스에 의존하고 있기때문에 기존 소스를 수정할 필요 없음.
    }

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV5", 15000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        // member 객체의 참조값이 보이는 것이 아니라 실 데이터가 보이는 이유는 롬복이 toString()을 통해 오버라이딩 해주기 때문
        log.info("member == findMember {}", member == findMember);
        log.info("member equals findMember {}", member.equals(findMember));
        assertThat(findMember).isEqualTo(member);
        //21:27:35.934 [main] INFO hello.jdbc.repository.MemberRepositoryV0Test - findMember=Member(memberId=memberV2, money=15000)
        //21:27:35.943 [main] INFO hello.jdbc.repository.MemberRepositoryV0Test - member == findMember false
        //21:27:35.943 [main] INFO hello.jdbc.repository.MemberRepositoryV0Test - member equals findMember true

        // 특정 회원의 money 변경하기
        // update : money 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        // 테스트를 위해 삭제하는 로직을 구성해도 이 이전의 로직에서 다른 예외가 터져버린다면, 삭제하는 로직이 실행이 안된다.
        // 테스트 간에는 이 부분을 유의.. -> 궁극적인 방법이 아님.
        repository.delete(member.getMemberId());
        // 삭제되었기 때문에 예외터짐
        Member deletedMember = repository.findById(member.getMemberId());
        // 해당 데이터가 없는 경우에는 NoSuchElementException 예외이용
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}