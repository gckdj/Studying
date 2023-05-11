package com.microservice.user.security;

import com.microservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;
    private final ObjectPostProcessor<Object> objectPostProcessor;

    private static final String[] WHITE_LIST = {
            "/users/**",
            "/",
            "/**"
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests(authorize -> {
                try {
                    authorize
                            .antMatchers("/**").permitAll()
                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            .requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll()
                            .and()
                            .addFilter(getAuthenticationFilter());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        );
        return http.build();
    }

    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
        authenticationFilter.setAuthenticationManager(authenticationManager(builder));
        return authenticationFilter;
    }

//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        // http.authorizeRequests().antMatchers("/users/**").permitAll();
//
//        // permitAll X!
//        // 스프링 2.7 이슈..
//        http.authorizeRequests()
//                .antMatchers("/**").hasIpAddress("192.168.0.1") // 아이피변경
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(getAuthenticationFilter());
//
//        // 프레임으로 나눠진 페이지(데이터베이스 콘솔) 차단해제
//        http.headers().frameOptions().disable();
//    }
//
//    private AuthenticationFilter getAuthenticationFilter() throws Exception {
//        AuthenticationFilter af = new AuthenticationFilter();
//        af.setAuthenticationManager(authenticationManager());
//
//        return af;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 사용자가 입력한 암호화된 값 == 디비에 저장된 값
//        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
//    }
}
