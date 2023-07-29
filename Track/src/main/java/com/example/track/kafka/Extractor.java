package com.example.track.kafka;

import com.example.global.config.BinanceConfig;
import com.example.global.config.KafkaConfig;
import com.example.global.exception.WhaleException;
import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.domain.kafka.TradeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String EXCHANGE_ORIGIN = "https://exchange.blockchain.com";
    private static final String WEBSOCKET_URI = "wss://ws.blockchain.info/mercury-gateway/v1/ws";
    private static final String CHANNEL_SUBSCRIPTION = "채널 구독 BTC-USD";

    private static final String ACTION = "subscribe";
    private static final String CHANNEL = "trades";
    private static final String SYMBOL = "BTC-USD";
    private static final int CONNECTION_TIMEOUT_MS = 10000;
    private final TrackSignalServiceImpl trackSignalServiceImpl;

    private final KafkaConfig kafkaConfig;

    private final BinanceConfig binanceConfig;

    @Autowired
    public Extractor(TrackSignalServiceImpl trackSignalServiceImpl, KafkaConfig kafkaConfig, BinanceConfig binanceConfig) {
        this.trackSignalServiceImpl = trackSignalServiceImpl;
        this.kafkaConfig = kafkaConfig;
        this.binanceConfig = binanceConfig;
    }

    public CountDownLatch start() throws WhaleException {
        CountDownLatch latch = new CountDownLatch(1);
        BinanceApiWebSocketListener listener = new BinanceApiWebSocketListener(latch, kafkaProducer(), trackSignalServiceImpl);
        HttpClient client = HttpClient.newHttpClient();

        WebSocket webSocket = client.newWebSocketBuilder().header("Origin", EXCHANGE_ORIGIN).connectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT_MS)).buildAsync(URI.create(WEBSOCKET_URI), listener).join();

        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("token", binanceConfig.getToken());
        msgMap.put("action", ACTION);
        msgMap.put("channel", CHANNEL);
        msgMap.put("symbol", SYMBOL);

        webSocket.sendText(new JSONObject(msgMap).toString(), true);
        LOGGER.info(CHANNEL_SUBSCRIPTION);
        return latch;
    }

    private TradeEventKafkaProducer kafkaProducer() throws WhaleException {
        Map<String, Object> kafkaConfigSetting = new HashMap<>();
        kafkaConfigSetting.put(ProducerConfig.ACKS_CONFIG, kafkaConfig.getAcks());
        kafkaConfigSetting.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServers());
        kafkaConfigSetting.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfig.getExtractor());
        KafkaProducer<String, TradeEvent> kafkaProducerService = new KafkaProducer<>(kafkaConfigSetting, new StringSerializer(), new TradeEventSerde());
        TradeEventKafkaProducer kafkaProducer = new TradeEventKafkaProducer(kafkaProducerService, kafkaConfig.getInputTopic());
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaProducer::close));
        return kafkaProducer;
    }
}
