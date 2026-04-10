package com.example.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayRateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(getClientId(exchange));
    }

    private String getClientId(ServerWebExchange exchange) {
        String clientId = exchange.getRequest().getHeaders().getFirst("client-id");

        if (clientId == null || clientId.isBlank()) {
            return "anonymous";
        }

        return clientId;
    }
}