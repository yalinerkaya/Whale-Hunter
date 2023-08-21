package com.example.track.domain.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public String getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(String lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(String seqnum) {
        this.seqnum = seqnum;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public static TradeEvent CurrentTrade (String lastClosePrice){
        TradeEvent tradeEvent = new TradeEvent();
        tradeEvent.setLastClosePrice(lastClosePrice);
        return  tradeEvent;
    }
}
