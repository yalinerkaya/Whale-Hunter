package com.example.display.domain;

import com.example.global.exception.WhaleException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * packageName    : com.example.track.kafka
 * fileName       : AggregateTradeDataSerde
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
public class AggregateTradeDataSerde implements Deserializer<AggregateTradeData>, Serializer<AggregateTradeData> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public AggregateTradeData deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.readValue(new String(data), AggregateTradeData.class);
        } catch (JsonProcessingException e) {
            throw new WhaleException();
        }
    }

    @Override
    public byte[] serialize(String topic, AggregateTradeData data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new WhaleException();
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
