package com.example.track.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.example.global.util.TrackConstants.TRADE_MESSAGE_TOPIC;

/**
 * packageName    : com.example.track.kafka
 * fileName       : TradeEventKafkaProducer
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Slf4j
public class TradeMessageKafkaProducer implements KafkaProducerService<String> {

    private final KafkaProducer<String, String> kafkaProducer;

    public TradeMessageKafkaProducer(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void send(String key, String data) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TRADE_MESSAGE_TOPIC, key, data);
        kafkaProducer.send(record, ((metadata, exception) -> {
            if (exception != null) {
                log.error("레코드 입력 실패, 사유 :", exception);
            } else {
                log.info("레코드 작성 성공! 파티션 : " + metadata.partition() + " 토픽 : " + metadata.topic());
            }
        }));
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
