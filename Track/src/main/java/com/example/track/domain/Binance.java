package com.example.track.domain;

/**
 * packageName    : com.example.track.domain
 * fileName       : Binance
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
public class Binance {
    private final String apiKey;
    private final String secretKey;
    private final String token;

    public Binance(String apiKey, String secretKey, String token) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getToken() {
        return token;
    }
}
