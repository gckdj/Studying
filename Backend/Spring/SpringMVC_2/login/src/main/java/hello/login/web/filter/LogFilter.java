package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@Slf4j
public class LogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
        //2022-07-07 22:48:07.325  INFO 9680 --- [           main] hello.login.web.filter.LogFilter         : log filter init
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }

    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }

    //2022-07-07 22:48:32.095  INFO 9680 --- [nio-8080-exec-1] hello.login.web.filter.LogFilter         : log filter doFilter
    //2022-07-07 22:48:32.095  INFO 9680 --- [nio-8080-exec-1] hello.login.web.filter.LogFilter         : REQUEST [536229f9-34c9-46ce-9a46-df2f9e45d19c][/session-info]
    //2022-07-07 22:48:32.116  INFO 9680 --- [nio-8080-exec-1] hello.login.web.filter.LogFilter         : RESPONSE [536229f9-34c9-46ce-9a46-df2f9e45d19c][/session-info]

    //http 개별요청에 대한 logback mdc <<-- 확인
}
