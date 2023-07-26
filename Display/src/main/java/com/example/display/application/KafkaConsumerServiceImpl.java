package com.example.display.application;

import com.example.display.domain.AggregateTradeData;
import com.example.global.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService<AggregateTradeData> {

    private final KafkaConsumer<String, AggregateTradeData> consumer;

    private final KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    public KafkaConsumerServiceImpl(KafkaConsumer<String, AggregateTradeData> consumer, KafkaConsumerConfig kafkaConsumerConfig) {
        this.consumer = consumer;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    @Override
    public List<AggregateTradeData> poll() {
        List<AggregateTradeData> result = new ArrayList<>();
        consumer.poll(Duration.ofMillis(150))
                .records(kafkaConsumerConfig.getTopic()).forEach(record -> result.add(record.value()));
        return result;
    }

    @Override
    public void commitOffsets() {
        consumer.commitSync();
    }
}