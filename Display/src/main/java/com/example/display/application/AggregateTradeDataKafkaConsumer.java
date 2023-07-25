package com.example.display.application;

import com.example.display.domain.AggregateTradeData;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class AggregateTradeDataKafkaConsumer implements KafkaConsumerService<AggregateTradeData> {

    private final KafkaConsumer<String, AggregateTradeData> consumer;

    @Value("${topic}")
    private String topic;

    @Autowired
    public AggregateTradeDataKafkaConsumer(KafkaConsumer<String, AggregateTradeData> consumer) {
        this.consumer = consumer;
    }

    @Override
    public List<AggregateTradeData> poll() {
        List<AggregateTradeData> result = new ArrayList<>();
        consumer.poll(Duration.ofMillis(150)).records(topic).forEach(record -> result.add(record.value()));
        return result;
    }

    @Override
    public void commitOffsets() {
        consumer.commitSync();
    }
}