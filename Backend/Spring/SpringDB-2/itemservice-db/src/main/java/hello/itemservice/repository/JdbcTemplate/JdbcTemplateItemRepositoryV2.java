package hello.itemservice.repository.JdbcTemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * NamedParameterJdbcTemplate
 * SqlParameterSource
 * - BeanPropertySqlParameterSource
 * - MapSqlParameterSource
 * - Map
 *
 * BeanPropertyRowMapper
 */

@Slf4j
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

    // private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name, price, quantity) values " +
                // ?, ?, ? 같은 순서기반 파라미터 X
                "(:itemName, :price, :quantity)";

        // 매개변수로 넘어간 객체를 파라미러로 사용
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);

        // KeyHolder는 auto increment로 생성되는 디비상의 키값을 쿼리실행 이후 가지고 있고 개발자가 다시 사용할 수 있게 해준다.
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";
        try {
            // 순수 자바 문법으로 파람을 넣어주는 방법
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name, price, quantity from item";
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }

        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%', :itemName, '%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }
        log.info("sql = {}", sql);
        return template.query(sql, param, itemRowMapper());
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item" +
                " set item_name = :itemName, price = :price, quantity = :quantity where " +
                "id = ?";

        // SqlParameterSource : 쿼리에 사용되는 파라미터를 담는 인터페이스
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);

        template.update(sql, mapSqlParameterSource);

    }

    private RowMapper<Item> itemRowMapper() {
        // BeanPropertyRowMapper : 반환할 객체에 맞춰서 매핑된 값을 반환(스프링 제공)
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
