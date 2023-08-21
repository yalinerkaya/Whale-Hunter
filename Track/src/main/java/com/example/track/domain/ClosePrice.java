package com.example.track.domain;

import com.example.track.dto.ClosePriceResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * packageName    : com.example.track.domain
 * fileName       : ClosePrice
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Entity
@Table(name = "close_price")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClosePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "close_price_uid")
    private Long closePriceUid;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "closing_price")
    private BigDecimal closingPrice;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ClosePrice(String symbol, BigDecimal closingPrice, LocalDateTime closedAt) {
        this.symbol = symbol;
        this.closingPrice = closingPrice;
        this.closedAt = closedAt;
    }

    public static ClosePrice createFromResponse(ClosePriceResponse response) {
        return new ClosePrice(response.getSymbol(), response.getClosingPrice(), response.getClosedAt());
    }
}