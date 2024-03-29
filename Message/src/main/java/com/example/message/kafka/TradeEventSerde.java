package com.example.message.kafka;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * packageName    : com.example.track.kafka
 * fileName       : TradeEventSerde
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Slf4j
public class TradeEventSerde implements Serializer<TradeEvent>, Deserializer<TradeEvent> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public TradeEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.readValue(new String(data), TradeEvent.class);
        } catch (JsonProcessingException e) {
            log.error("역직렬화에 실패했습니다. 사유 : " + e);
            throw new WhaleException(WhaleExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] serialize(String topic, TradeEvent data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("직렬화에 실패했습니다. 사유 : " + e);
            throw new WhaleException(WhaleExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
