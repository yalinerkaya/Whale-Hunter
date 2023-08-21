package com.example.message.kafka;

import com.example.message.application.MessageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.global.util.MessageConstants.DOWN;
import static com.example.global.util.MessageConstants.UP;

/**
 * packageName    : com.example.message.domain
 * fileName       : KafkaMessageConsumer
 * author         : Jay
 * date           : 2023-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-21        Jay       최초 생성
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

        if(current.equals(UP) && lastBtcStatus.equals(DOWN)){
            messageService.priceBreakout();
        }

        if(current.equals(DOWN) && lastBtcStatus.equals(UP)){
            messageService.priceBreakdown();
        }
    }
}