package com.example.message.dto;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * packageName    : com.example.message.dto
 * fileName       : CoinMessageRequest
 * author         : Jay
 * date           : 2023-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26        Jay       최초 생성
 */

@Getter
@Setter
public class CoinMessageRequest {
    private final Message message;
    public CoinMessageRequest(Message message) {
        this.message = message;
    }
}
