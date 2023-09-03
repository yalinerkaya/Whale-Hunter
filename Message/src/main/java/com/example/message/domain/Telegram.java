package com.example.message.domain;

import com.example.global.common.StatusCode;
import com.example.global.config.TelegramConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static org.telegram.telegrambots.meta.ApiConstants.BASE_URL;

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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Telegram {
    private String chat;
    private String token;
    private String name;
    public static URL buildSendMessageUrl(String message, TelegramConfig telegramConfig) throws MalformedURLException, UnsupportedEncodingException {
        String urlString =
            String.format(
                    "%s%s/sendMessage?chat_id=%s&text=%s",
                    BASE_URL,
                    telegramConfig.getToken(),
                    telegramConfig.getChat(),
                    URLEncoder.encode(message, "UTF-8"));
        return new URL(urlString);
    }
}
