package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    // 스프링이 제공하는 방식은 준수하고, 너무 깊게 생각할 필요없음
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                // 모든 URL에 패턴적용, 서블릿에서 제공하는 것과는 약간 다르다.
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                // 제외할 패턴 -> 로그인 세션이 없는 매핑들 + css, ico 파일
                .excludePathPatterns("/", "/member/add", "/login", "/logout", "/css/**", "/*.ico");

        //2022-07-09 20:20:50.530  INFO 14280 --- [nio-8080-exec-2] h.l.w.interceptor.LoginCheckInterceptor  : 인증 체크 인터셉터 실행 /error
        //2022-07-09 20:20:50.726  INFO 14280 --- [nio-8080-exec-2] h.l.w.interceptor.LoginCheckInterceptor  : 미인증 사용자 요청
    }

    // @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        // 모든 URL에 패턴적용
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        // 모든 URL에 패턴적용
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    //2022-07-08 21:22:12.584  INFO 10056 --- [nio-8080-exec-1] hello.login.web.filter.LogFilter         : log filter doFilter
    //2022-07-08 21:22:12.585  INFO 10056 --- [nio-8080-exec-1] hello.login.web.filter.LogFilter         : REQUEST [05ea8cbb-9bce-4a12-a8d1-ad0d34047b71][/]
    //2022-07-08 21:22:12.585  INFO 10056 --- [nio-8080-exec-1] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 시작 /
    //2022-07-08 21:22:12.834  INFO 10056 --- [nio-8080-exec-1] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 종료/
    //2022-07-08 21:22:12.834  INFO 10056 --- [nio-8080-exec-1] hello.login.web.filter.LogFilter         : RESPONSE [05ea8cbb-9bce-4a12-a8d1-ad0d34047b71][/]
    //2022-07-08 21:22:12.879  INFO 10056 --- [nio-8080-exec-2] hello.login.web.filter.LogFilter         : log filter doFilter
    //2022-07-08 21:22:12.880  INFO 10056 --- [nio-8080-exec-2] hello.login.web.filter.LogFilter         : REQUEST [3b9d0713-2610-434c-92ec-9fda2bd774f0][/css/bootstrap.min.css]
    //2022-07-08 21:22:12.880  INFO 10056 --- [nio-8080-exec-2] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 시작 /css/bootstrap.min.css
    //2022-07-08 21:22:12.896  INFO 10056 --- [nio-8080-exec-2] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 종료/css/bootstrap.min.css
    //2022-07-08 21:22:12.896  INFO 10056 --- [nio-8080-exec-2] hello.login.web.filter.LogFilter         : RESPONSE [3b9d0713-2610-434c-92ec-9fda2bd774f0][/css/bootstrap.min.css]
    //2022-07-08 21:22:12.955  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LogFilter         : log filter doFilter
    //2022-07-08 21:22:12.955  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LogFilter         : REQUEST [cf47b52d-bc00-4dc3-83cb-24ff615c7715][/favicon.ico]
    //2022-07-08 21:22:12.955  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 시작 /favicon.ico
    //2022-07-08 21:22:12.955  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LoginCheckFilter  : 인증 체크 로직 실행 /favicon.ico
    //2022-07-08 21:22:12.955  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LoginCheckFilter  : 미인증 사용자 요청 /favicon.ico
    //2022-07-08 21:22:12.956  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 종료/favicon.ico
    //2022-07-08 21:22:12.956  INFO 10056 --- [nio-8080-exec-3] hello.login.web.filter.LogFilter         : RESPONSE [cf47b52d-bc00-4dc3-83cb-24ff615c7715][/favicon.ico]
    //2022-07-08 21:22:12.959  INFO 10056 --- [nio-8080-exec-4] hello.login.web.filter.LogFilter         : log filter doFilter
    //2022-07-08 21:22:12.959  INFO 10056 --- [nio-8080-exec-4] hello.login.web.filter.LogFilter         : REQUEST [b660f1c3-e7a2-4042-8e86-9fd37ad2941d][/login]
    //2022-07-08 21:22:12.959  INFO 10056 --- [nio-8080-exec-4] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 시작 /login
    //2022-07-08 21:22:12.989  INFO 10056 --- [nio-8080-exec-4] hello.login.web.filter.LoginCheckFilter  : 인증 체크 필터 종료/login
    //2022-07-08 21:22:12.989  INFO 10056 --- [nio-8080-exec-4] hello.login.web.filter.LogFilter         : RESPONSE [b660f1c3-e7a2-4042-8e86-9fd37ad2941d][/login]
}
