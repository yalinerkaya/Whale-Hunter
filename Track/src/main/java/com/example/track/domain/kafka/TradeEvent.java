package com.example.track.domain.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * packageName    : com.example.track.kafka
 * fileName       : TradeEvent
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeEvent {
    private String seqnum;
    private String event;
    private String symbol;
    private String text;
    private String channel;
    private String timestamp;
    private String side;
    private String qty;
    private String price;
    private String lastClosePrice;
}
