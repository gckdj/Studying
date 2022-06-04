package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
/* 롬복에서 지원하는 어노테이션으로, 생성자 생략가능*/
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    스프링에 의해 생성자로 ItemRepository 객체가 생성 및 현재 클래스로 주입
//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 파라미터로 넘어온 id를 @PathVariable 로 받아 사용
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        // id 를 통해 리포지토리 내에 정보를 가져오고 model 객체에 담아 뷰페이지로 이동
        // 뷰에서는 타임리프로 받아 렌더링
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 테스트용 데이터 추가
    // PostConstruct 는 스프링이 초기화될때 메서드를 먼저 호출시킴
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
