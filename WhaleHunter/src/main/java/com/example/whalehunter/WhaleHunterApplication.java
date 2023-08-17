package com.example.whalehunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class WhaleHunterApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhaleHunterApplication.class, args);
    }
}
