package com.example.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class CoinStatusResponse {
    private String status;
    public CoinStatusResponse() {
    }

    public CoinStatusResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
