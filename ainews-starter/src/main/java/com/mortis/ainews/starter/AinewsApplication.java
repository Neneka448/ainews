package com.mortis.ainews.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.mortis.ainews.web", // Web层包含Controllers
        "com.mortis.ainews.domain", // Domain层包含Services
        "com.mortis.ainews.infra", // Infra层包含Repository实现
        "com.mortis.ainews.starter" // Starter层本身
})
@EnableJpaRepositories(basePackages = "com.mortis.ainews.infra.persistence.repository.interfaces")
@EntityScan(basePackages = "com.mortis.ainews.infra.persistence.po")
public class AinewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AinewsApplication.class, args);
    }

}
