package com.example.track.application;

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
public interface KafkaProducerService<T> {
    void send(String key, T record);

    void close();
}
