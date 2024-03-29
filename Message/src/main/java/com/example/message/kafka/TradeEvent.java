package com.example.message.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String tradeId;
    private String signalType;

    public TradeEvent(String tradeId, String signalType) {
        this.tradeId = tradeId;
        this.signalType = signalType;
    }
}
