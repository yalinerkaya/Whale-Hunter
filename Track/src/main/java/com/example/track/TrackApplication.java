package com.example.track;

import com.example.global.exception.WhaleException;
import com.example.track.kafka.Extractor;
import com.example.track.kafka.StreamProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TrackApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TrackApplication.class, args);

        try {
            Extractor.start();
        } catch (WhaleException e) {
            e.getStackTrace();
        }

        try {
            StreamProcessor.start();
        } catch (WhaleException e) {
            e.getStackTrace();
        }

    }

}
