package com.example.apigateway.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/rate-limit-exceeded")
    public ResponseEntity<Map<String, Object>> rateLimitExceeded() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("error", "Too Many Requests");
        response.put("message", "Rate limit exceeded. Please try again later.");
        response.put("status", 429);
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }
}