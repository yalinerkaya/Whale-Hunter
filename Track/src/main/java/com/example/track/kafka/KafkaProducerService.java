package com.example.track.kafka;

/**
 * packageName    : com.example.track.kafka
 * fileName       : KafkaProducerService
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
public interface KafkaProducerService<T> {
    /**
     * Writes a record to the Kafka broker using the given key.
     *
     * @param key    The key to associate the data item with.
     * @param record The data item to be written to Kafka.
     */
    void send(String key, T record);

    /**
     * Closes the underlying Kafka producer. Typically called when the system is shutting down.
     */
    void close();
}
