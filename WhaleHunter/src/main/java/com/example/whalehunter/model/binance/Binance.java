package com.example.whalehunter.model.binance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * Binance.java
 *
 * @author Jay
 */
@NoArgsConstructor
@AllArgsConstructor
public class Binance {
    private String apiKey;
    private String secretKey;

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
