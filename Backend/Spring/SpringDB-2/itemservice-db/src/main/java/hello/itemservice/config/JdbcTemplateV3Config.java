package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.JdbcTemplate.JdbcTemplateItemRepositoryV3;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV3Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV3(dataSource);
    }

    //2022-09-16 22:10:03.801 DEBUG 5205 --- [nio-8080-exec-3] o.s.jdbc.datasource.DataSourceUtils      : Fetching JDBC Connection from DataSource
    //2022-09-16 22:10:03.803 DEBUG 5205 --- [nio-8080-exec-3] o.s.jdbc.core.BeanPropertyRowMapper      : Mapping column 'ID' to property 'id' of type 'java.lang.Long'
    //2022-09-16 22:10:03.804 DEBUG 5205 --- [nio-8080-exec-3] o.s.jdbc.core.BeanPropertyRowMapper      : Mapping column 'ITEM_NAME' to property 'itemName' of type 'java.lang.String'
    //2022-09-16 22:10:03.804 DEBUG 5205 --- [nio-8080-exec-3] o.s.jdbc.core.BeanPropertyRowMapper      : Mapping column 'PRICE' to property 'price' of type 'java.lang.Integer'
    //2022-09-16 22:10:03.805 DEBUG 5205 --- [nio-8080-exec-3] o.s.jdbc.core.BeanPropertyRowMapper      : Mapping column 'QUANTITY' to property 'quantity' of type 'java.lang.Integer'
}
