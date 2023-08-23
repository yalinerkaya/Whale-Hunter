package com.example.global.config;

import com.example.message.domain.Telegram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * packageName    : com.example.global.config
 * fileName       : TelegramConfig
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Getter
@Setter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfig {

    private String token;
    private String chat;
    private String name;

    @Bean(name = "telegram")
    public Telegram telegramClient() {
        return new Telegram(this.token, this.chat, this.name);
    }
}


