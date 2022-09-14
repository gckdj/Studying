package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.JdbcTemplate.JdbcTemplateItemRepositoryV1;
import hello.itemservice.repository.memory.MemoryItemRepository;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }

    //2022-09-14 21:31:44.102  INFO 2492 --- [           main] h.itemservice.ItemServiceApplication     : Starting ItemServiceApplication using Java 17.0.4.1 on djdjui-MacBookAir.local with PID 2492 (/Users/djdj/Documents/GitHub/Studying/Backend/Spring/SpringDB-2/itemservice-db/out/production/classes started by djdj in /Users/djdj/Documents/GitHub/Studying/Backend/Spring/SpringDB-2/itemservice-db)
    //2022-09-14 21:31:44.104  INFO 2492 --- [           main] h.itemservice.ItemServiceApplication     : The following 1 profile is active: "local"
    //2022-09-14 21:31:44.492  INFO 2492 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
    //2022-09-14 21:31:44.496  INFO 2492 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
    //2022-09-14 21:31:44.496  INFO 2492 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.60]
    //2022-09-14 21:31:44.536  INFO 2492 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
    //2022-09-14 21:31:44.536  INFO 2492 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 407 ms
    //2022-09-14 21:31:44.663  INFO 2492 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
    //2022-09-14 21:31:44.734  INFO 2492 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
    //2022-09-14 21:31:44.739  INFO 2492 --- [           main] h.itemservice.ItemServiceApplication     : Started ItemServiceApplication in 0.817 seconds (JVM running for 1.06)
    //2022-09-14 21:31:44.740  INFO 2492 --- [           main] hello.itemservice.TestDataInit           : test data init
    //2022-09-14 21:31:44.740 DEBUG 2492 --- [           main] o.s.jdbc.core.JdbcTemplate               : Executing SQL update and returning generated keys
    //2022-09-14 21:31:44.741 DEBUG 2492 --- [           main] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL statement
    //2022-09-14 21:31:44.742 DEBUG 2492 --- [           main] o.s.jdbc.datasource.DataSourceUtils      : Fetching JDBC Connection from DataSource
    //2022-09-14 21:31:44.742  INFO 2492 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    //2022-09-14 21:31:44.768  INFO 2492 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    //2022-09-14 21:31:44.782 DEBUG 2492 --- [           main] o.s.jdbc.core.JdbcTemplate               : Executing SQL update and returning generated keys
    //2022-09-14 21:31:44.782 DEBUG 2492 --- [           main] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL statement
    //2022-09-14 21:31:44.782 DEBUG 2492 --- [           main] o.s.jdbc.datasource.DataSourceUtils      : Fetching JDBC Connection from DataSource
}
