package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    // 테스트 실행 전에 무조건 저장된 store를 비운다.
    @AfterEach
    void afterEach() {
        itemRepository.storeClear();
    }

    @Test
    void save() {
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);
    }

    @Test
    void update() {

    }
}
