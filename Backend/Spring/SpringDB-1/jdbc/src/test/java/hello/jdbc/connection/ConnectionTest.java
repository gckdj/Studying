package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());

        //12:07:13.156 [main] INFO hello.jdbc.connection.ConnectionTest - connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
        //12:07:13.158 [main] INFO hello.jdbc.connection.ConnectionTest - connection = conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManagerDataSource - 항상 새로운 커넥션을 획득하는 example
        // DriverManager에는 DataSource 인터페이스 미구현 -> 스프링에서 대안으로 DriverManagerDataSource 제공
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);

        //12:10:19.196 [main] DEBUG org.springframework.jdbc.datasource.DriverManagerDataSource - Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
        //12:10:19.292 [main] DEBUG org.springframework.jdbc.datasource.DriverManagerDataSource - Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
        //12:10:19.295 [main] INFO hello.jdbc.connection.ConnectionTest - connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
        //12:10:19.296 [main] INFO hello.jdbc.connection.ConnectionTest - connection = conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // HicariCP : 스프링부트2부터 기본값으로 권장하는 커넥션풀
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(URL);
        dataSource.setPassword(PASSWORD);
        dataSource.setUsername(USERNAME);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("djPool");

        useDataSource(dataSource);
        Thread.sleep(1000);

        //12:20:02.394 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn2: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.397 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn3: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.399 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn4: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.404 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn5: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.407 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn6: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.410 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn7: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.414 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn8: url=jdbc:h2:tcp://localhost/~/test user=SA
        //12:20:02.417 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Added connection conn9: url=jdbc:h2:tcp://localhost/~/test user=SA

        // 커넥션을 넘겨준 2개는 active, 대기하고 있는 풀 8개는 idle
        // 12:20:02.417 [djPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - After adding stats (total=10, active=2, idle=8, waiting=0)

        // 최대치를 초과한 11개를 연결시키면 1개는 waiting
        // 12:25:54.773 [djPool housekeeper] DEBUG com.zaxxer.hikari.pool.HikariPool - djPool - Pool stats (total=10, active=10, idle=0, waiting=1)
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        Connection con3 = dataSource.getConnection();
        Connection con4 = dataSource.getConnection();
        Connection con5 = dataSource.getConnection();
        Connection con6 = dataSource.getConnection();
        Connection con7 = dataSource.getConnection();
        Connection con8 = dataSource.getConnection();
        Connection con9 = dataSource.getConnection();
        Connection con10 = dataSource.getConnection();
        Connection con11 = dataSource.getConnection();

        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());
    }
    
    // DriverManager vs DataSource
    // DataSource는 getConnection()만 호출하면 끝 but. DriverManager는 관련 설정 정보를 계속해서 인자로 넘겨줘야함
    // DataSource는 '설정' 과 '사용'이 분리되어있음 -> 관련 개발자들이 관련 속성정보를 잘못입력하는 등의 개발과정에서 혼란 X
}
