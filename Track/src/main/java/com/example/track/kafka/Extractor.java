package com.example.track.kafka;

import com.example.global.exception.WhaleException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


/**
 * packageName    : com.example.track.kafka
 * fileName       : Extractor
 * author         : 정재윤
 * date           : 2023-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-25        정재윤       최초 생성
 */
@Component
public class Extractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Extractor.class);

    public static void main(String[] args) {
        try {
            start().await();
        } catch (WhaleException | InterruptedException e) {
            LOGGER.error("웹소켓 연결에 실패했습니다. 사유 : ", e);
        }
    }

    public static CountDownLatch start() throws WhaleException {
        CountDownLatch latch = new CountDownLatch(1);
        BinanceApiWebSocketListener listener = new BinanceApiWebSocketListener(latch, kafkaProducer());
        HttpClient client = HttpClient.newHttpClient();

        WebSocket webSocket = client.newWebSocketBuilder()
                .header("Origin", "https://exchange.blockchain.com")
                .connectTimeout(Duration.ofMillis(10000))
                .buildAsync(URI.create("wss://ws.blockchain.info/mercury-gateway/v1/ws"), listener)
                .join();

        webSocket.sendText(subscribeMsg(), true);
        LOGGER.info("채널 구독 BTC-USD");
        return latch;
    }

    private static TradeEventKafkaProducer kafkaProducer() throws WhaleException {
        Map<String, Object> kafkaConfig = new HashMap<>();
        kafkaConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        kafkaConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
        kafkaConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "crypto-data-extractor");
        KafkaProducer<String, TradeEvent> kafkaProducerService = new KafkaProducer<>(kafkaConfig, new StringSerializer(), new TradeEventSerde());
        TradeEventKafkaProducer kafkaProducer = new TradeEventKafkaProducer(kafkaProducerService, "trade_events");
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaProducer::close));
        return kafkaProducer;
    }

    private static String subscribeMsg() {
        return "{\n"
                + "  \"token\": \"{{TOKEN}}}\",\n"
                + "  \"action\": \"subscribe\",\n"
                + "  \"channel\": \"trades\",\n"
                + "  \"symbol\": \"BTC-USD\"\n"
                + "}";
    }
}
