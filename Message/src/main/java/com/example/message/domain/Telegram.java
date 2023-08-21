package com.example.message.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
@AllArgsConstructor
public class Telegram {
    private final String chat;
    private final String token;
    private final String name;
}
