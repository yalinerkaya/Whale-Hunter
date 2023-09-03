package com.example.message.dto;

import com.example.global.common.StatusCode;
import com.example.global.util.MessageConstants;
import com.example.message.kafka.TradeEvent;
import lombok.*;

import javax.validation.constraints.NotNull;

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
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class MessageEventRequest {
    @NotNull
    private Long tradeUid;
    private String symbol;
    private String status;
    @NotNull
    private String signal;

    public static MessageEventRequest generateEvent(TradeEvent tradeEvent){
        MessageEventRequest messageEventRequest = new MessageEventRequest();
        messageEventRequest.setTradeUid(Long.valueOf(tradeEvent.getTradeId()));
        messageEventRequest.setSignal(tradeEvent.getSignalType());
        messageEventRequest.setSymbol(MessageConstants.SYMBOL_BTC_USDT);
        messageEventRequest.setStatus(StatusCode.WAIT.getValue());
        return messageEventRequest;
    }
}
