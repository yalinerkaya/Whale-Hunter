package com.example.global.config;

import com.example.message.domain.Kafka;
import com.example.message.kafka.TradeErrorKafkaProducer;
import com.example.message.kafka.TradeEvent;
import com.example.message.kafka.TradeEventSerde;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.global.config
 * fileName       : KafkaConfig
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProduceConfig {
    private String inputTopic;
    private String bootstrapServers;
    private String acks;
    private String extractor;
    private String processor;

    @Bean(name = "kafka")
    public Kafka KafkaClient() {
        return new Kafka(this.inputTopic, this.bootstrapServers, this.acks, this.extractor, this.processor);
    }

    @Bean(name = "kafkaErrorProduce")
    public TradeErrorKafkaProducer createTradeErrorKafkaProducer() {
        Map<String, Object> kafkaConfigSetting = new HashMap<>();
        kafkaConfigSetting.put(ProducerConfig.ACKS_CONFIG, this.getAcks());
        kafkaConfigSetting.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.getBootstrapServers());
        kafkaConfigSetting.put(ProducerConfig.CLIENT_ID_CONFIG, this.getExtractor());
        KafkaProducer<String, TradeEvent> kafkaProducerService = new KafkaProducer<>(kafkaConfigSetting, new StringSerializer(), new TradeEventSerde());
        TradeErrorKafkaProducer kafkaProducer = new TradeErrorKafkaProducer(kafkaProducerService);
        return kafkaProducer;
    }
}
