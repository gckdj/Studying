package com.spring.usercloud.config;

import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Resilience4JConfig {
    @Bean
    public Customizer<> globalCustomConfiguration() {

        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.
    }
}
