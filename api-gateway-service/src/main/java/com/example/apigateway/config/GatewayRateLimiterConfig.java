package com.example.apigateway.config;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayRateLimiterConfig {

    @Bean
    public KeyResolver ipAddressKeyResolver() {
        return exchange -> Mono.just(resolveClientKey(exchange));
    }

    private String resolveClientKey(ServerWebExchange exchange) {
        String clientId = exchange.getRequest().getHeaders().getFirst("X-Client-Id");
        if (clientId != null && !clientId.isBlank()) {
            return "client-id:" + clientId.trim();
        }

        String forwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return "ip:" + forwardedFor.split(",")[0].trim();
        }

        return Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                .map(InetSocketAddress::getAddress)
                .map(address -> "ip:" + address.getHostAddress())
                .orElse("unknown-client");
    }
}