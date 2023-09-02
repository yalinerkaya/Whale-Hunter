package com.example.global.config;

import com.example.track.kafka.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
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
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String inputTopic;
    private String outputTopic;
    private String servers;
    private String acks;
    private String extractor;
    private String processor;

    @Bean(name = "kafka")
    public Kafka KafkaClient() {
        return new Kafka(this.inputTopic, this.outputTopic, this.servers, this.acks, this.extractor, this.processor);
    }
    @Bean(name = "kafkaCommonProduce")
    public TradeMessageKafkaProducer createTradeEventKafkaProducer() {
        Map<String, Object> kafkaConfigSetting = new HashMap<>();
        kafkaConfigSetting.put(ProducerConfig.ACKS_CONFIG, this.getAcks());
        kafkaConfigSetting.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.getServers());
        kafkaConfigSetting.put(ProducerConfig.CLIENT_ID_CONFIG, this.getExtractor());
        kafkaConfigSetting.put(ProducerConfig.RETRIES_CONFIG, 3); // 3회 재시도
        KafkaProducer<String, String> kafkaProducerService = new KafkaProducer<>(kafkaConfigSetting, new StringSerializer(), new TestEventSerde());
        TradeMessageKafkaProducer kafkaProducer = new TradeMessageKafkaProducer(kafkaProducerService);
        return kafkaProducer;
    }
    @Bean(name = "kafkaErrorProduce")
    public TradeErrorKafkaProducer createTradeErrorKafkaProducer() {
        Map<String, Object> kafkaConfigSetting = new HashMap<>();
        kafkaConfigSetting.put(ProducerConfig.ACKS_CONFIG, this.getAcks());
        kafkaConfigSetting.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.getServers());
        kafkaConfigSetting.put(ProducerConfig.CLIENT_ID_CONFIG, this.getExtractor());
        KafkaProducer<String, TradeEvent> kafkaProducerService = new KafkaProducer<>(kafkaConfigSetting, new StringSerializer(), new TradeEventSerde());
        TradeErrorKafkaProducer kafkaProducer = new TradeErrorKafkaProducer(kafkaProducerService);
        return kafkaProducer;
    }
}
