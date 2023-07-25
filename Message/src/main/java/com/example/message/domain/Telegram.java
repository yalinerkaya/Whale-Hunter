package com.example.message.domain;

import java.net.URLEncoder;
import java.time.LocalDateTime;

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
public class Telegram {
    private final String chat;
    private final String token;

    public Telegram(String chat, String token) {
        this.chat = chat;
        this.token = token;
    }

    public String getChat() {
        return chat;
    }

    public String getToken() {
        return token;
    }

}
