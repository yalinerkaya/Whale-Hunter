package com.example.global.config;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

/**
 * packageName    : com.example.global.config
 * fileName       : TrackLoadblanceConfig
 * author         : Jay
 * date           : 2023-08-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-16        Jay       최초 생성
 */
@LoadBalancerClient(value = "message-service")
public class MessageLoadblanceConfig {
    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
