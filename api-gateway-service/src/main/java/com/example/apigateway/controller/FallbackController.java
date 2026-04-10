package com.example.apigateway.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/ratelimit")
    public ResponseEntity<Map<String, Object>> rateLimitFallback() {
        return ResponseEntity.status(429).body(
                Map.of(
                        "error", "Too Many Requests",
                        "message", "Rate limit exceeded. Please try again later.",
                        "status", 429
                )
        );
    }
}