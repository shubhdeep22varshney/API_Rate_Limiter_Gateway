package com.example.demobackend.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("hello")
    public ResponseEntity<Map<String, Object>> hello() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Hello from demo backend service");
        response.put("service", "demo-backend-service");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("health-check")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "demo-backend-service"));
    }
    @GetMapping("slow")
    public ResponseEntity<String> slow() throws InterruptedException {
        Thread.sleep(2000);
        return ResponseEntity.ok("Slow API response");
    }
}
