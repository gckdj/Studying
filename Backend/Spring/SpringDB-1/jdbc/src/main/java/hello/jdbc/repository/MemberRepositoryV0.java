package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class MemberRepositoryV0 {

    // 로우레벨 코딩
    public Member save(Member member) throws SQLException {

        // 파라미터 바인딩 방식을 사용해야하는 이유는 sql injection 때문
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            // 이렇게 처리하면 둘 다 동시에 처리가 안될 수 있음
            // pstmt.close(); // Exception
            // con.close();

            // 쿼리 실행 후 쿼리정리
            // 쿼리 종료시에는 역순으로..
            close(con, pstmt, null);
        }
    }

    private void close(Connection  con, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DBConnectionUtil.getConnection();
    }
}
