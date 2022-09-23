package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
// @Repository 주석 O
// java.lang.IllegalArgumentException: org.hibernate.hql.internal.ast.QuerySyntaxException: unexpected token: selectxxx near line 1, column 1 [selectxxx i from hello.itemservice.domain.Item i]

// @Repository 주석 X
// org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.hql.internal.ast.QuerySyntaxException: unexpected token: selectxxx near line 1, column 1 [selectxxx i from hello.itemservice.domain.Item i]; nested exception is java.lang.IllegalArgumentException: org.hibernate.hql.internal.ast.QuerySyntaxException: unexpected token: selectxxx near line 1, column 1 [selectxxx i from hello.itemservice.domain.Item i]

// @Repository가 있으면 예외변한 AOP의 대상이 된다.
// JPA 예외를 스프링 예외로 추상화하고 예외를 서비스 계층으로 전달

// JPA에서는 트랜잭셔널 항상 필요하다고 생각
@Transactional
public class JpaItemRepositoryV1 implements ItemRepository {

    // JPA의 모든 동작은 엔티티매니저를 통해 이루어진다.
    // 복잡한 추가설정들은 스프링부트가 처리
    private final EntityManager em;

    public JpaItemRepositoryV1(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    // @Commit 없으면 캐시상에서 업데이트, 실제 반영하려면 주석해제
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = em.find(Item.class, itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        // item i로 지정된 i 엔티티를 반환
        String jpql = "select i from Item i";

        // 동적쿼리
        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where";
        }

        boolean andFlag = false;

        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                jpql += " and";
            }
            jpql += " i.price <= :maxPrice";
        }

        log.info("jpql={}", jpql);

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);

        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName", itemName);
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        return query.getResultList();
    }
}
