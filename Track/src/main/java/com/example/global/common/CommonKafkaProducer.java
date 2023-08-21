package com.example.global.common;

import com.example.track.kafka.TestEventSerde;
import com.example.track.kafka.TradeTestKafkaProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.global.common
 * fileName       : CommonKafkaProducer
 * author         : 정재윤
 * date           : 2023-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-21        정재윤       최초 생성
 */
@Component
public class CommonKafkaProducer {

    @Autowired
    public CommonKafkaProducer() {

    }
    public TradeTestKafkaProducer createTradeEventKafkaProducer() {
        Map<String, Object> kafkaConfigSetting = new HashMap<>();
        kafkaConfigSetting.put(ProducerConfig.ACKS_CONFIG, "all");
        kafkaConfigSetting.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
        kafkaConfigSetting.put(ProducerConfig.CLIENT_ID_CONFIG, "extractor");
        KafkaProducer<String, String> kafkaProducerService = new KafkaProducer<>(kafkaConfigSetting, new StringSerializer(), new TestEventSerde());
        TradeTestKafkaProducer kafkaProducer = new TradeTestKafkaProducer(kafkaProducerService);
        return kafkaProducer;
    }
}
