package com.example.message.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.track.domain
 * fileName       : Kafka
 * author         : Jay
 * date           : 2023-07-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-27        Jay       최초 생성
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Kafka {
    private String inputTopic;
    private String bootstrapServers;
    private String acks;
    private String extractor;
    private String processor;
}
