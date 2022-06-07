package hello.servlet.Web.springmvc.v3;

import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    // ModelAndView 말고 반환값이 String인 경우 알아서 뷰로 반환한다.
    // 좋은 개발방법은 아니지만, GET POST 메서드를 구분짓지 않아도 통신에 지장은 없다.
    // method는 아래와 같이 명시한다.
    @RequestMapping(value =  "/new-form", method = RequestMethod.GET)
    public String newForm() {
        return "new-form";
    }

    @RequestMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    // 개인정보 저장과 같은 민감한 사항은 POST 방식으로 처리
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @PostMapping("/save") << 동일한 방법
    public String save
            (@RequestParam("username") String username,
             // 형변환까지 자동지원
             @RequestParam("age") int age,
             // 데이터는 Model 객체를 활용해서 넘겨줌
             Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }
    // 좋은 개발방법은 아니지만, GET POST 메서드를 구분짓지 않아도 통신에 지장은 없다.
}
