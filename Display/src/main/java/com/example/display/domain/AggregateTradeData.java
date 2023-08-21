package com.example.display.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AggregateTradeData {
    private String time;
    private double aggregateSales;
    private double aggregateBuys;
}
