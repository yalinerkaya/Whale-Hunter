package com.example.track.kafka;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.track.domain.kafka.AggregateTradeData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * packageName    : com.example.track.kafka
 * fileName       : AggregateTradeDataSerde
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@Slf4j
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
            log.error("역직렬화에 실패했습니다. 사유 : " + e);
            throw new WhaleException(WhaleExceptionType.INVALID_DESERIALIZE);
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
            log.error("직렬화에 실패했습니다. 사유 : " + e);
            throw new WhaleException(WhaleExceptionType.INVALID_SERIALIZE);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
