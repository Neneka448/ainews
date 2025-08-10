package com.mortis.ainews.web.controller;

import com.mortis.ainews.web.dto.HealthResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/actuator/health")
    public HealthResponse health() {
        return new HealthResponse();
    }
}
