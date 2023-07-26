package com.example.track.domain.kafka;

import com.example.global.config.KafkaConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.track.domain
 * fileName       : Kafka
 * author         : 정재윤
 * date           : 2023-07-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-27        정재윤       최초 생성
 */
@Setter
@Getter
@NoArgsConstructor
public class Kafka {
    private String inputTopic;
    private String outputTopic;
    private String servers;
    private String acks;
    private String extractor;
    private String processor;

    public Kafka(String inputTopic, String outputTopic, String servers, String acks, String extractor, String processor) {
        this.inputTopic = inputTopic;
        this.outputTopic = outputTopic;
        this.servers = servers;
        this.acks = acks;
        this.extractor = extractor;
        this.processor = processor;
    }
}
