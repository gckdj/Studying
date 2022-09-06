package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

// MemberRepository에서 예외를 구현하려면, 인터페이스에서도 예외를 선언해야함.
// -> 인터페이스에서 예외를 선언해버리면 의존성이 생기기게 되고 JDBC가 아닌 기술로 변경하면 인터페이스 자체를 변경해야함.
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}