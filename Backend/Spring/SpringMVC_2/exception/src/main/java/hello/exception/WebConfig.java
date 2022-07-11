package hello.exception;

import hello.exception.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
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
