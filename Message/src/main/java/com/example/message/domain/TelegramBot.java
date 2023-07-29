package com.example.message.domain;

import com.example.global.config.TelegramConfig;
import com.example.message.MessageApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private static final String COMMAND_BTC = "/btc";
    private static final String MESSAGE_TEXT_BTC = "BTC 떡상 기원";
    private static final String LOCALHOST_ENDPOINT = "http://localhost:8003/btc";

    private final TelegramConfig telegramConfig;

    @Autowired
    public TelegramBot(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (COMMAND_BTC.equals(messageText)) {
                callLocalhostController();
                String chatId = update.getMessage().getChat().getId().toString();
                sendMessageToTelegram(MESSAGE_TEXT_BTC, chatId);
            }
        }
    }

    public void sendMessageToTelegram(String messageText, String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void callLocalhostController() {
        WebClient.create(LOCALHOST_ENDPOINT).post().retrieve().bodyToMono(String.class).doOnError(error -> {
            log.info("에러 발생 : " + error);
        }).subscribe(responseBody -> {
            log.info("로컬호스트 컨트롤러로부터의 응답: " + responseBody);
        });
    }
}