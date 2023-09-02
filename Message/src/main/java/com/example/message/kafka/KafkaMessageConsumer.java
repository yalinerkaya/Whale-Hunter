package com.example.message.kafka;

import com.example.message.application.MessageService;
import com.example.message.dto.MessageEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.DataInput;

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
@Slf4j
public class KafkaMessageConsumer {
    private final MessageService messageService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaMessageConsumer(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "trade_test", groupId = "data-api")
    public void consumeTradeEvent(ConsumerRecord<String, Object> record) throws Exception {
        try {
            TradeEvent tradeEvent = objectMapper.readValue(record.value().toString(), TradeEvent.class);
            MessageEventRequest messageEventRequest = MessageEventRequest.generateEvent(tradeEvent);
            messageService.insertMessageEvent(messageEventRequest);

            if (tradeEvent.getSignalType().equals(UP)) {
                messageService.priceBreakout(messageEventRequest);
            }

            if (tradeEvent.getSignalType().equals(DOWN)) {
                messageService.priceBreakdown(messageEventRequest);
            }

        } catch (Exception exception) {
            // 실패 event produce (보상 트랜잭션)
            log.info(exception.getMessage());
        }
    }
}