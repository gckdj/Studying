package hello.typeconverter.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {

        Form form = new Form();

        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());

        model.addAttribute("form", form);
        return "formatter-form";
    }

    // 클라에서 값이 서버로 붙을 때에도 포맷이 적용됨
    // @ModelAttribute에서 붙일 클래스를 찾고 해당 클래스에 정의된 포맷이 있으면 붙을 때도 적용
    @PostMapping("/formatter/edit")
    public String formatterForm(@ModelAttribute Form form) {
        return "formatter-view";
    }

    // 스프링의 기본제공 포맷터가 붙어줌
    @Data
    static class Form {
        @NumberFormat(pattern = "###,###")
        private Integer number;

        // 월에 해당하는 MM은 대문자로 쓰는 것이 국제표준
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }

    // @RequestParam, @ResponseBody들은 Jackson 라이브러리를 통해 값이 들어오므로, 스프링 자체에 내장되어 있는 포맷터가 아니라
    // 해당 라이브러리에서 제공하는 포맷터를 적용해야한다.
}
