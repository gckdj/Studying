package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // 인터셉터에서는 구현메서드가 구분되어 있기 때문에, request에 속성을 정해두고 꺼내쓴다.
        request.setAttribute(LOG_ID, uuid);

        // @RequestMapping : HandlerMethod
        // 정적리소스 : ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            // 호출할 컨트롤러 메서드의 모든 정보가 포함됨.
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        //2022-07-09 20:09:19.089  INFO 1660 --- [nio-8080-exec-4] h.login.web.interceptor.LogInterceptor   : REQUEST [1d6b37e6-212c-4c72-8455-cc30b76c153a][/login][hello.login.web.login.LoginController#loginForm(LoginForm)]
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
        //2022-07-09 20:09:19.096  INFO 1660 --- [nio-8080-exec-4] h.login.web.interceptor.LogInterceptor   : postHandle [ModelAndView [view="login/loginForm"; model={loginForm=LoginForm(loginId=null, password=null), org.springframework.validation.BindingResult.loginForm=org.springframework.validation.BeanPropertyBindingResult: 0 errors}]]
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);

        //2022-07-09 20:09:19.119  INFO 1660 --- [nio-8080-exec-4] h.login.web.interceptor.LogInterceptor   : RESPONSE [1d6b37e6-212c-4c72-8455-cc30b76c153a][/login][hello.login.web.login.LoginController#loginForm(LoginForm)]
        if(ex != null) {
            log.error("afterCompletion error!", ex);
        }
    }
}
