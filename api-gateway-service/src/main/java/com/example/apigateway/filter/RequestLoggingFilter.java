package com.example.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String method = request.getMethod().name();
        String path = request.getURI().getPath();
        String clientId = request.getHeaders().getFirst("client-id");

        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }

        final String finalClientId = clientId;

        log.info("Incoming request -> method: {}, path: {}, client-id: {}", method, path, finalClientId);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    if (exchange.getResponse().getStatusCode() != null) {
                        log.info("Outgoing response -> status: {}, path: {}, client-id: {}",
                                exchange.getResponse().getStatusCode().value(),
                                path,
                                finalClientId);
                    }
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}