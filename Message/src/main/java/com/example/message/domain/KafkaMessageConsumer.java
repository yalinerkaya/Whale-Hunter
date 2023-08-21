package com.example.message.domain;

import com.example.message.application.MessageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.message.domain
 * fileName       : KafkaMessageConsumer
 * author         : 정재윤
 * date           : 2023-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-21        정재윤       최초 생성
 */
@Component
public class KafkaMessageConsumer {
    private final MessageService messageService;

    public KafkaMessageConsumer(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "trade_test", groupId = "data-api")
    public void consumeTradeEvent(String current) throws Exception {
        String lastBtcStatus = messageService.selectBTCStatus().getStatus();
        messageService.priceBreakout();
    }
}