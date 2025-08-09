package com.mortis.ainews.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    // Simple text probe
    @GetMapping("/act/healthz")
    public String health() {
        return "OK";
    }
}
