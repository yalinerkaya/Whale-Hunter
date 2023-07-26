package com.example.global.config;

import com.example.track.domain.kafka.Kafka;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.example.global.config
 * fileName       : KafkaConfig
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String inputTopic;
    private String outputTopic;
    private String servers;
    private String acks;
    private String extractor;
    private String processor;

    @Bean(name = "kafka")
    public Kafka KafkaClient() {
        return new Kafka(this.inputTopic, this.outputTopic, this.servers, this.acks, this.extractor, this.processor);
    }
}
