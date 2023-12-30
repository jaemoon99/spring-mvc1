package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final에 관한 생성자 자동 생성
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired //생성자가 하나일 경우 생략가능
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
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

        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);

        model.addAttribute("item", item); // 자동 추가 -> 생략 가능

        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        /**
         * ModelAttribute name을 생략하면 매개변수 클래스의 첫글자를 소문자로 바꿔서 사용 ex) Item -> item
         */
        itemRepository.save(item);

        return "basic/addForm";
    }

    @PostMapping("/add")
    public String addItemV4(Item item) {
        /**
         * @RequestParam, @ModelAttribute를 생략할 경우 일반적인 타입은 param으로 다른 것은 modelattribute로 적용
         */
        itemRepository.save(item);

        return "basic/addForm";
    }

    /*
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
