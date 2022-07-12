package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 스프링 인터셉터 적용
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**" //오류 페이지 경로
                );
    }

    // @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        //filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        //2022-07-11 20:26:49.048  INFO 14536 --- [nio-8080-exec-1] hello.exception.filter.LogFilter         : REQUEST [89f70137-d26f-4696-9510-bcdbc3077dd4][REQUEST][/]
        //2022-07-11 20:26:49.056  INFO 14536 --- [nio-8080-exec-1] hello.exception.filter.LogFilter         : RESPONSE [89f70137-d26f-4696-9510-bcdbc3077dd4][REQUEST][/]
        return filterRegistrationBean;
    }
}
