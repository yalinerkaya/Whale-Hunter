package com.example.track.domain.kafka;

import lombok.*;

/**
 * packageName    : com.example.track.kafka
 * fileName       : AggregateTradeData
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateTradeData {
    private String time;
    private double aggregateSales;
    private double aggregateBuys;
}
