package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

// 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스
// 구현체는 XML파일로 대체
@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto itemUpdateDto);

    List<Item> findAll(ItemSearchCond itemSearch);

    // 어노테이션으로 조회
    // XML으로 정의하고 있는 경우가 대다수이므로, 거의 사용하지는 않는다.
    // @Select("select id, item_name, price, quantity from item where id = #{id}")
    // ${} 표기법은 ?를 대체하는 파라미터 바인딩 방식이 아니라 컬럼명, 테이블명도 동적으로 처리할 수 있는 장점이 있지만
    // SQL 인젝션 공격 위험성이 있어 극히 드문 경우에 사용해야한다.
    Optional<Item> findById(Long id);
}
