package com.example.display;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.example.display")
@ComponentScan(basePackages = {"com.example.display", "com.example.global.config"})
public class DisplayApplication {
    public static void main(String[] args) {
        SpringApplication.run(DisplayApplication.class, args);
    }

}
