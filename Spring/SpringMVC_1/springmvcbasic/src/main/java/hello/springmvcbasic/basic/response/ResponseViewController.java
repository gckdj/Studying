package hello.springmvcbasic.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        /* 받은 값을 바인딩 → view 파일 내 ${data} */
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return mav;
    }
    //<body>
    //	<!-- 렌더링되면 empty 값이 ${data}의 값으로 치환-->
    //	<p>hello!</p>
    //</body>

    /* Model에 값을 담고 반환값에 뷰의 논리경로를 써주는 경우 */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    /* 컨트롤러의 매핑경로와 뷰의 논리 경로가 같으면 뷰를 반환 */
    /* 명확하지 않아서 사용권장 X */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
