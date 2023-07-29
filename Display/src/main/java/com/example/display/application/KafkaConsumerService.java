package com.example.display.application;

import java.util.List;

/**
 * packageName    : com.example.display.application
 * fileName       : KafkaConsumerService
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */

public interface KafkaConsumerService<T> {
    List<T> poll();
    void commitOffsets();
}
