package com.example.track.domain;

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
 * fileName       : MoveAverage
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Entity
@Table(name = "move_average")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveAverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "move_average_uid")
    private Long moveAverageUid;

    @Column(name = "close_price_uid")
    private Long closePriceUid;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "move_average")
    private BigDecimal moveAverage;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public MoveAverage(Long closePriceUid, String symbol, BigDecimal moveAverage, LocalDateTime closedAt) {
        this.closePriceUid = closePriceUid;
        this.symbol = symbol;
        this.moveAverage = moveAverage;
        this.closedAt = closedAt;
    }

}