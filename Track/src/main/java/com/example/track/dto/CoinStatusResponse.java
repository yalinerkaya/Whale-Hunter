package com.example.track.dto;

import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoinStatusResponse {
    private String status;
}
