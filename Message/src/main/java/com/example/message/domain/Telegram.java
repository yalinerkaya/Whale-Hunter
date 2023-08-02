package com.example.message.domain;

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

    private final String name;

    public Telegram(String chat, String token, String name) {
        this.chat = chat;
        this.token = token;
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
