package hello.itemservice.domain;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
// 일일이 롤백 달아서 처리하는 수고로움 해결
// 원래는 로직이 성공적으로 수행되면 커밋해야되지만, 테스트에서는 한정적으로 테스트가 '종료'되면 롤백 시켜버린다.
@Transactional
// 자신 상위의 @SpringBootApplication을 탐색, 사용
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    // 트랜잭션 활용을 통한 테스트격리
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // status = transactionManager.getTransaction(new DefaultTransactionAttribute());
    }

    @AfterEach
    void afterEach() {
        // MemoryItemRepository 의 경우 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) itemRepository).clearStore();
        }

        // 트랜잭션 롤백
        // transactionManager.rollback(status);
        //2022-09-17 11:00:05.633 DEBUG 2402 --- [           main] o.s.jdbc.support.JdbcTransactionManager  : Rolling back JDBC transaction on Connection [HikariProxyConnection@1570578713 wrapping conn0: url=jdbc:h2:tcp://localhost/~/testcase user=SA]
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("item1", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        ItemUpdateDto updateParam = new ItemUpdateDto("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId).get();
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void findItems() {
        //given
        Item item1 = new Item("itemA-1", 10000, 10);
        Item item2 = new Item("itemA-2", 20000, 20);
        Item item3 = new Item("itemB-1", 30000, 30);

        log.info("repository = {}", itemRepository.getClass());
        // repository = class hello.itemservice.repository.jpa.JpaItemRepositoryV1$$EnhancerBySpringCGLIB$$deb34819

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        //둘 다 없음 검증
        test(null, null, item1, item2, item3);
        test("", null, item1, item2, item3);

        //itemName 검증
        test("itemA", null, item1, item2);
        test("temA", null, item1, item2);
        test("itemB", null, item3);

        //maxPrice 검증
        test(null, 10000, item1);

        //둘 다 있음 검증
        test("itemA", 10000, item1);
    }

    void test(String itemName, Integer maxPrice, Item... items) {
        List<Item> result = itemRepository.findAll(new ItemSearchCond(itemName, maxPrice));
        assertThat(result).containsExactly(items);
    }

    // 디비에 실제 사용하고 있는 데이터가 있어서 테스트가 실패하는 상황
    // 해결방안
    // 1. 개발디비서버파기 -> 테스트를 한번 더 실행하면 개발서버에도 데이터가 누적되고 테스트에 실패
    //    - 테스트의 중요원칙 위배(테스트는 반복가능, 테스트는 다른 테스트와 격리)
    //    - 일일이 테스트 종료 후 데이터를 삭제하는건 원인해결이 되지 않는다(테스트 도중 예외가 발생해서 테스트가 실패된다면?)
}
