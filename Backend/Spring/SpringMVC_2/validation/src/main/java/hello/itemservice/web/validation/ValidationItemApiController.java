package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            // 검증 중 담긴 모든 오류 반환
            // 형식 : json 객체
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;

        // 기존의 ModelAttribute 방식의 error 반환은 각 지정된 필드로 세밀하게 적용 가능하지만,
        // api 방식의 httpMessageConverter 는 전체객체단위로 적용이 되어서 Item 객체를 만들다 터져버리면 예외로 발생해버리고 이후의 검증이 진행안됨
    }
}
