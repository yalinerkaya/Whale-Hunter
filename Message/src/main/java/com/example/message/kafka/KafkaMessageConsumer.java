package com.example.message.kafka;

import com.example.message.application.MessageService;
import com.example.message.dto.MessageEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.DataInput;

import static com.example.global.util.MessageConstants.*;

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
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer {
    private final MessageService messageService;
    private final TradeErrorKafkaProducer kafkaErrorProduce;
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "trade_test", groupId = "data-api")
    public void consumeTradeEvent(ConsumerRecord<String, Object> record) throws Exception {

        TradeEvent tradeEvent = objectMapper.readValue(record.value().toString(), TradeEvent.class);
        MessageEventRequest messageEventRequest = MessageEventRequest.generateEvent(tradeEvent);

        try {
            if (!messageService.selectCompletedEvent(messageEventRequest)) {
                // 멱등성 체크를 통과하지 못한 경우에만 로직 실행
                messageService.insertMessageEvent(messageEventRequest);

                if (tradeEvent.getSignalType().equals(UP)) {
                    messageService.priceBreakout(messageEventRequest);
                }

                if (tradeEvent.getSignalType().equals(DOWN)) {
                    messageService.priceBreakdown(messageEventRequest);
                }
            }

        } catch (Exception exception) {
            log.info(exception.getMessage());
            kafkaErrorProduce.send(TRADE_ERROR_TOPIC, tradeEvent);
        }
    }
}