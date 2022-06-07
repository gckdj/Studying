package hello.servlet.Web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 스프링이 자동으로 스프링 빈 이름을 통해 핸들러매핑
// RequestMapping 으로 이어짐
@Component("/springmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        // viewName과 일치하는 빈이 있는지 먼저 탐색하고
        // 없으면 ViewResolver을 통해 뷰를 반환
        return new ModelAndView("new-form");
    }
}
