package com.example.apigateway.exception;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = resolveStatus(ex);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("error", status.getReasonPhrase());
        response.put("message", buildMessage(status));
        response.put("status", status.value());
        response.put("path", exchange.getRequest().getPath().value());
        response.put("timestamp", LocalDateTime.now().toString());

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(response);
        } catch (Exception e) {
            bytes = """
                    {"error":"Internal Server Error","message":"Unable to process error response","status":500}
                    """.getBytes(StandardCharsets.UTF_8);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private HttpStatus resolveStatus(Throwable ex) {
        if (ex instanceof ResponseStatusException responseStatusException) {
            if (responseStatusException.getStatusCode() instanceof HttpStatus httpStatus) {
                return httpStatus;
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String buildMessage(HttpStatus status) {
        if (status == HttpStatus.TOO_MANY_REQUESTS) {
            return "Rate limit exceeded. Please try again later.";
        }
        return "Something went wrong while processing the request.";
    }
}