package com.study.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// 커스텀필터 적용하기 -> 각 서비스별 yml에 지정
@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//            log.info("Global Pre Filter: request id -> {}", config.getBaseMessage());
//
//            if (config.isPreLogger()) {
//                log.info("Global Filter Start: request id -> {}", request.getId());
//            }
//
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if (config.isPostLogger()) {
//                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
//                }
//            }));
//        });

        GatewayFilter gatewayFilter = new OrderedGatewayFilter((exchange, chain) -> {
            // 필터의 내용정리
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter: BaseMessage -> {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging PRE Filter: request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
                }
            }));
            // 최우선 로깅순서
        }, Ordered.HIGHEST_PRECEDENCE);

        return gatewayFilter;
    }

    @Data
    public static class Config {
        // Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
