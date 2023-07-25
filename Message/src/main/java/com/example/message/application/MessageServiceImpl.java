package com.example.message.application;

import com.example.global.config.TelegramConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.message.domain.Telegram;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.global.util.MessageConstants.FIFTY_AVERAGE_BREAKOUT;
import static com.example.global.util.MessageConstants.TELEGRAM_BASE_URL;


@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public void sendMessage() throws Exception {

        TelegramConfig telegramConfig = new TelegramConfig();
        Telegram telegram = telegramConfig.telegramClient();

        String urlString = TELEGRAM_BASE_URL + telegram.getToken() +
                "/sendMessage?chat_id=" + telegram.getChat() +
                "&text=" + URLEncoder.encode(FIFTY_AVERAGE_BREAKOUT, "UTF-8");

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new WhaleException(WhaleExceptionType.MESSAGE_ERROR_SEND);
        }

        conn.disconnect();
    }
}
