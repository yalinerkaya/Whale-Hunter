package com.example.global.config;

import com.example.display.domain.AggregateTradeData;
import com.example.display.domain.AggregateTradeDataSerde;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.global.config
 * fileName       : KafkaConsumerConfig
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
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutocommit;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private String maxPollRecords;

    @Value("${spring.kafka.consumer.max-poll-interval-ms}")
    private String maxPollIntervalMs;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.session-timeout-ms}")
    private String sessionTimeoutMs;

    @Value("${topic}")
    private String topic;

    private KafkaConsumer<String, AggregateTradeData> kafkaConsumer;

    @Bean
    public KafkaConsumer<String, AggregateTradeData> kafkaConsumerBean() {

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutocommit);
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);

        kafkaConsumer = new KafkaConsumer<>(config, new StringDeserializer(), new AggregateTradeDataSerde());
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        return kafkaConsumer;
    }

    @PreDestroy
    private void close() {
        kafkaConsumer.close();
    }
}
