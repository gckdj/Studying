package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DBConnectionUtilTest {

    @Test
    void connection() throws SQLException {
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
        // 테스트 전에 h2 실행필수!
        //20:36:43.929 [main] INFO hello.jdbc.connection.DBConnectionUtil - get connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
    }
}
