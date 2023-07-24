package com.example.track.dto;

import lombok.Getter;
import lombok.Setter;

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
@Setter
public class ClosePriceResponse {
    private final String symbol;
    private final BigDecimal closingPrice;
    private final LocalDateTime closedAt;

    public ClosePriceResponse(String symbol, BigDecimal closingPrice, LocalDateTime closedAt) {
        this.symbol = symbol;
        this.closingPrice = closingPrice;
        this.closedAt = closedAt;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }
}
