package com.buildhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuildHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuildHubApplication.class, args);
        System.out.println("🚀 BuildHub Server running on port 8080");
        System.out.println("📱 API: http://localhost:8080/api");
    }
}

