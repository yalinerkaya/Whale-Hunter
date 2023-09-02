package com.example.track.kafka;

/**
 * packageName    : com.example.track.kafka
 * fileName       : KafkaProducerService
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
public interface KafkaProducerService {
    void send(String key, TradeEvent record);

    void send(String key, byte[] record);

    void close();
}
