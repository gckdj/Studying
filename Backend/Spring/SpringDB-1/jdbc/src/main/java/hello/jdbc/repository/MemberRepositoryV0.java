package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class MemberRepositoryV0 {

    // 로우레벨 코딩
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        con = getConnection();
        con.prepareStatement()
    }

    private Connection getConnection() throws SQLException {
        return DBConnectionUtil.getConnection();
    }
}
