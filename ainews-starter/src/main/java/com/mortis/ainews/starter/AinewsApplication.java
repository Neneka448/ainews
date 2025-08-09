package com.mortis.ainews.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mortis.ainews")
public class AinewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AinewsApplication.class, args);
    }
}
