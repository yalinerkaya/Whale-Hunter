package com.example.whalehunter.global.config;

import com.example.whalehunter.model.binance.Binance;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BinanceConfig.java
 *
 * @author Jay
 */
@Configuration
@ConfigurationProperties(prefix = "binance")
public class BinanceConfig {

    private String apiKey;

    private String secretKey;

    @Bean(name="binance")
    public Binance getKakaoSocial(){
        return new Binance(this.apiKey, this.secretKey);
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
}
