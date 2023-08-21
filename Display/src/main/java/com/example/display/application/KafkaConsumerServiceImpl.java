package com.example.display.application;

import com.example.display.kafka.AggregateTradeData;
import com.example.global.config.KafkaConsumerConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService<AggregateTradeData> {

    private final KafkaConsumer<String, AggregateTradeData> consumer;

    private final KafkaConsumerConfig kafkaConsumerConfig;


    @Override
    public List<AggregateTradeData> poll() {
        List<AggregateTradeData> result = new ArrayList<>();
        consumer.poll(Duration.ofMillis(150))
                .records(kafkaConsumerConfig.getTopic())
                .forEach(record -> result.add(record.value()));
        return result;
    }

    @Override
    public void commitOffsets() {
        consumer.commitSync();
    }
}