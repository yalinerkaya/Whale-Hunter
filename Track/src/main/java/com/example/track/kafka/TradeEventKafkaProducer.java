package com.example.track.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

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
public class TradeEventKafkaProducer implements KafkaProducerService {

    private final KafkaProducer<String, TradeEvent> kafkaProducer;
    private final String topic;

    public TradeEventKafkaProducer(KafkaProducer<String, TradeEvent> kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    @Override
    public void send(String key, TradeEvent data) {
        ProducerRecord<String, TradeEvent> record = new ProducerRecord<>(topic, key, data);
        kafkaProducer.send(record, ((metadata, exception) -> {
            if (exception != null) {
                log.error("레코드 입력 실패, 사유 :", exception);
            } else {
                log.info("레코드 작성 성공! 파티션 : " + metadata.partition() + " 토픽 : " + metadata.topic() + "|" + metadata.offset());
            }
        }));
    }

    @Override
    public void send(String key, byte[] record) {

    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
