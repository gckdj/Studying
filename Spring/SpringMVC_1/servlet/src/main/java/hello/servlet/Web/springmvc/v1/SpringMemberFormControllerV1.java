package hello.servlet.Web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 스프링이 자동으로 Controller 는 컴포넌트로 등록 → Controller 에노테이션 내에 컴포넌트 에노테이션이 붙어있음
// 동일하게 인식하는 예) 클래스 레벨에 붙여놔야됨
// @Component
// @RequestMapping
// 빈으로 직접 컨트롤러를 등록하는 방법 등 방법은 다양한데, 컨트롤러 쓰면된다, 실무 99%
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
