package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV1;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    
    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV1(em);
    }

    //2022-09-22 23:39:18.291 DEBUG 2206 --- [           main] org.hibernate.SQL                        : insert into item (id, item_name, price, quantity) values (default, ?, ?, ?)
    //2022-09-22 23:39:18.293 TRACE 2206 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [itemA]
    //2022-09-22 23:39:18.293 TRACE 2206 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [INTEGER] - [10000]
    //2022-09-22 23:39:18.293 TRACE 2206 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [INTEGER] - [10]
    //2022-09-22 23:39:18.301 DEBUG 2206 --- [           main] org.hibernate.SQL                        : insert into item (id, item_name, price, quantity) values (default, ?, ?, ?)
    //2022-09-22 23:39:18.302 TRACE 2206 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [itemB]
    //2022-09-22 23:39:18.302 TRACE 2206 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [INTEGER] - [20000]
    //2022-09-22 23:39:18.302 TRACE 2206 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [INTEGER] - [20]

}
