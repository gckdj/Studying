package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
//        if (bindingResult.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login? {}", loginMember);
//
//        // reject -> global error
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 로그인 성공처리
//        // 쿠키에 담긴 정보는 클라이언트에서 서버로 전송할때에 계속해서 같이 정보를 보내준다.
//        // 헤더정보, 앱저장소의 쿠키정보에서 확인가능
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);
//
//        return "redirect:/";
//    }

    // 세션관리가 적용된 로그인로직
//    @PostMapping("/login")
//    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
//        if (bindingResult.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login? {}", loginMember);
//
//        // reject -> global error
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 세션관리자를 통해 세션생성 및 회원데이터 보관
//        // 로그인 시에 생성된 UUID 는 KEY, loginMember 객체는 VALUE 로 저장
//        sessionManager.createSession(loginMember, response);
//
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);
//
//        return "redirect:/";
//    }

    // 세션관리가 적용된 로그인로직
//    @PostMapping("/login")
//    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login? {}", loginMember);
//
//        // reject -> global error
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 서블릿이 제공하는 세션사용 로직
//        // 세션이 있으면 세션을 반환하고 없으면 신규 세션을 생성한다.
//        // getSession(true) -> default cf) false일 경우에는 기존 세션이 없으면 새로운 세션 생성 X
//        HttpSession session = request.getSession();
//        // 세션에 로그인 회원정보를 보관
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//
//        return "redirect:/";
//    }

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "memberId");
//        return "redirect:/";
//    }

//    @PostMapping("/logout")
//    public String logoutV2(HttpServletRequest request) {
//        sessionManager.expireSession(request);
//        return "redirect:/";
//    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // 세션을 찾아보고 세션이 없으면 null이 반환
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션이 있다면 제거
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(
            @Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:" + redirectURL;
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie("memberId", null);
        // 쿠키 유지시간을 0으로 변경해서 제거
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // 단, 이런식의 쿠키는 브라우저에서 직접 사용자가 값을 변경할 수 있음. -> 위변조 가능
        // 쿠키에 보관된 정보는 보안에 취약하다 -> 쿠키의 개인정보, 신용카드 같은 것들을 저장하면 네트워크 전송 간에 털릴 수 있음.
        // 쿠키로 개발된 웹앱은 탈취된 쿠키로 지속적으로 악의적 요청가능

        // 대안 : 서버에서 토큰을 통해 랜덤값을 생성, 부여하고 관리해서 쿠키에 저장하더라도 예측불가능한 값을 부여한다.
        // 또한 쿠키를 털어가더라도 서버에서 쿠키 유지시간을 짧게 유지하고 해킹이 의심되는 경우 해당 토큰은 강제로 제거(ex 해외에서의 접속시도)
    }
}