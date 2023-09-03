package com.example.track.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.utils.Bytes;

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
public class TradeMessageKafkaProducer implements KafkaProducerService {

    private final KafkaProducer<String, byte[]> kafkaProducer;
    private final TradeEventSerde tradeEventSerde = new TradeEventSerde();

    public TradeMessageKafkaProducer(KafkaProducer<String, byte[]> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void send(String key, TradeEvent data) {
        byte[] eventSerilize = tradeEventSerde.serialize(TRADE_MESSAGE_TOPIC, data);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(TRADE_MESSAGE_TOPIC, key, eventSerilize);
        kafkaProducer.send(record, ((metadata, exception) -> {
            if (exception != null) {
                log.error("레코드 입력 실패, 사유 :", exception);
            } else {
                log.info("레코드 작성 성공! 파티션 : " + metadata.partition() + " 토픽 : " + metadata.topic());
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
