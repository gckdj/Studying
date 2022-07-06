package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;


    // @GetMapping("/")
    public String home() {
        return "home";
    }

    // 요청정보에서 쿠키 값을 받아와도 되지만 스프링에서 어노테이션으로 지원
//    public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model) {
//
//        // 쿠키에서 꺼낸 멤버정보가 없으면 홈으로
//        if (memberId == null) {
//            return "home";
//        }
//
//        Member loginMember = memberRepository.findById(memberId);
//
//        // 쿠키에서 꺼낸 멤버정보가 기존 저장소에 없다면 홈으로
//        if(loginMember == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }

//    public String homeLoginV2(HttpServletRequest request, Model model) {
//
//        // 세션관리자에 저장된 회원정보 조회
//        // 요청에서 sessionId 로 표기된 쿠키를 찾고 있다면, 해당 sessionId 가 key 인 value 값(member 객체) 반환
//        Member member = (Member) sessionManager.getSession(request);
//
//        // 로그인
//        if (member == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", member);
//        return "loginHome";
//
//        // mySessionId : afd49c74-f4ed-474d-b472-77edd8aac4f7
//        // 당연히도 서블릿에서도 세션을 지원한다.
//    }

//    public String homeLoginV3(HttpServletRequest request, Model model) {
//        // true로 해버리면 접속만 해도 세션이 생성되기에 false 처리
//        HttpSession session = request.getSession(false);
//
//        // 세션이 null 이거나
//        if (session == null) {
//            return "home";
//        }
//
//        Member loginMember= (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        // 찾은 세션내에서 로그인 멤버가 없으면 홈으로
//        if (loginMember == null) {
//            return "home";
//        }
//
//        // 예외상황에 걸리지 않으면 모델에 속성설정
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//   }

    // 스프링이 제공하는 세션기능을 사용한 로직
    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
