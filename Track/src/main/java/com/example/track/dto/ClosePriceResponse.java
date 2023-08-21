package com.example.track.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * packageName    : com.example.track.dto
 * fileName       : ClosePriceResponse
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-23        Jay       최초 생성
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClosePriceResponse {
    private String symbol;
    private BigDecimal closingPrice;
    private LocalDateTime closedAt;
}
