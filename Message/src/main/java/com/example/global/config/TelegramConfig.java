package com.example.global.config;

import com.example.message.domain.Telegram;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TelegramConfig.java
 *
 * @author Jay
 */
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfig {

    private String token;

    private String chat;

    @Bean(name = "telegram")
    public Telegram telegramClient() {
        return new Telegram(this.token, this.chat);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}


