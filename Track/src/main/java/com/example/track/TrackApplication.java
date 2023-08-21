package com.example.track;

import com.example.global.config.BinanceConfig;
import com.example.global.config.KafkaConfig;
import com.example.global.exception.WhaleException;
import com.example.track.application.TrackSignalService;
import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.kafka.Extractor;
import com.example.track.kafka.StreamProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
@EnableEurekaClient
@EnableScheduling
@EnableCaching
@EnableAsync
@ComponentScan(basePackages = {"com.example.track", "com.example.global.config"})
@SpringBootApplication
public class TrackApplication {

    public static void main(String[] args){
        ApplicationContext context = SpringApplication.run(TrackApplication.class, args);

        try {
            TrackSignalService trackSignalService = context.getBean(TrackSignalService.class);
            KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
            BinanceConfig binanceConfig = context.getBean(BinanceConfig.class);
            Extractor extractor = new Extractor((TrackSignalServiceImpl) trackSignalService, kafkaConfig, binanceConfig);
            extractor.start();
        } catch (WhaleException e) {
            e.getStackTrace();
        }

        try {
            KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
            StreamProcessor streamProcessor = new StreamProcessor(kafkaConfig);
            streamProcessor.startKafkaStreams();
        } catch (WhaleException e) {
            e.getStackTrace();
        }

    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
