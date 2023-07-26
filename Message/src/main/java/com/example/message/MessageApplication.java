package com.example.message;

import com.example.global.config.TelegramConfig;
import com.example.message.domain.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.message", "com.example.global.config"})
public class MessageApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MessageApplication.class, args);
        TelegramConfig telegramConfig = context.getBean(TelegramConfig.class);
        TelegramBot telegramBot = new TelegramBot(telegramConfig);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
