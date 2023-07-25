package com.example.global.config;

import com.example.track.domain.Binance;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.example.global.config
 * fileName       : BinanceConfig
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-23        Jay       최초 생성
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "binance")
public class BinanceConfig {

    private String apiKey;

    private String secretKey;

    private String token;

    @Bean(name = "binance")
    public Binance binanceClient() {
        return new Binance(this.apiKey, this.secretKey, this.token);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
