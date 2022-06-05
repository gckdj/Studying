package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // GET, POST 요청방식에 따라 같은 URL 이라도 다른 메서드 호출
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // form에서 전송한 데이터는 form 내부에서 지정된 name 값을 key로 삼는다.
    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /* ModelAttribute에 입력된 객체프로퍼티에 파라미터로 넘어온 값이 붙어준다.*/
    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        // @ModelAttribute("item") -> item 이란 model 객체에 자동으로 값을 넣어줌
        // 저장소에 신규입력한 정보를 넣어주고, 신규입력한 정보를 가진 모델이 반환되는 뷰로 이동
        // model.addAttribute("item", item);
        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        // 클래스명의 첫글자를 소문자로 바꾸고 모델 객체의 변수명으로 부여 ex) Item -> item, HelloData -> helloData
        itemRepository.save(item);
        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV4(Item item) {
        // ModelAttribute 는 생략가능(단, 사용자가 정의한 객체인 경우)
        itemRepository.save(item);
        return "basic/item";
    }

    // redirect로 상품등록 후 제품상세페이지 뷰로 이동 -> 이동하지 않을 경우 결과창에서 새로고침을 하면 계속해서 같은 서버요청 발생
    // PRG Post/Redirect/Get 방식
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        /* PathVariable 변수 사용가능*/
        return "redirect:/basic/items/{itemId}";
    }

    // 테스트용 데이터 추가
    // PostConstruct 는 스프링이 초기화될때 메서드를 먼저 호출시킴
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
