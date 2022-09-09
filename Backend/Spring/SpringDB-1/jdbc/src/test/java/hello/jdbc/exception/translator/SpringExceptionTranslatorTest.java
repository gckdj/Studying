package hello.jdbc.exception.translator;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
public class SpringExceptionTranslatorTest {

    DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    @Test
    void sqlExceptionErrorCode() {
        String sql = "select bad grammer";

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();
        } catch(SQLException e) {
            assertThat(e.getErrorCode()).isEqualTo(42122);

            int errorCode = e.getErrorCode();
            // 에러코드를 하나씩 확인하고 예외를 만드는 것은 현실성이 없음.
            log.info("errorCode = {}", errorCode);
            log.info("error", e);
        }
    }

    @Test
    void exceptionTranslator() {
        String sql = "select bad grammer";

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();
        } catch (SQLException e) {
            assertThat(e.getErrorCode()).isEqualTo(42122);

            SQLErrorCodeSQLExceptionTranslator exTranslator = new SQLErrorCodeSQLExceptionTranslator();
            DataAccessException resultEx = exTranslator.translate("select", sql, e);
            log.info("resultEx", resultEx);
            //21:05:01.606 [main] INFO hello.jdbc.exception.translator.SpringExceptionTranslatorTest - resultEx
            //org.springframework.jdbc.BadSqlGrammarException: select; bad SQL grammar [select bad grammer]; nested exception is org.h2.jdbc.JdbcSQLSyntaxErrorException: Column "BAD" not found; SQL statement:
            //select bad grammer [42122-212]

            // BadSqlGrammerException이 예외로 반환되었음.
            // 스프링은 각 예외를 추상화한 DataAccessException 클래스를 제공
            // sql-error-codes.xml에 각 DB에 맞는 관련 설정정보들을 보유
            assertThat(resultEx.getClass()).isEqualTo(BadSqlGrammarException.class);

            // 서비스, 컨트롤러 계층에서 예외 처리가 필요한 경우 특정기술에 종속적인 SQLException 같은 예외를 직접 사용하는 것이 아니라,
            // 스프링이 제공하는 데이터 접근 예외를 사용하면 된다.
        }
    }
}
