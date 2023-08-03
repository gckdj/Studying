package com.microservice.user.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    Environment env;

    @Autowired
    public FeignErrorDecoder(Environment env) {
        this.env = env;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400: {
                break;
            }
            case 404: {
                if (methodKey.contains("getOrders")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                        env.getProperty("order_service.exception.orders_is_empty"));
                }
                //  "message": "사용자의 주문정보가 없습니다.",
                //  "path": "/users/d9c75d25-3697-4312-8b66-11a39cdb0e6d"
                break;
            }
            default:
                return new Exception(response.reason());
        }

        // 스위치에 분기되지 않으면 null 반환
        return null;
    }
}
