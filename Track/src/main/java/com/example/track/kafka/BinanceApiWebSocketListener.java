package com.example.track.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

/**
 * packageName    : com.example.track.kafka
 * fileName       : BinanceApiWebSocketListener
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@Slf4j
public class BinanceApiWebSocketListener implements WebSocket.Listener {
    private static final Logger LOGGER = LoggerFactory.getLogger(BinanceApiWebSocketListener.class);
    private final CountDownLatch latch;
    private final TradeEventKafkaProducer kafkaProducer;
    private final ObjectMapper mapper = new ObjectMapper();
    private StringBuilder builder = new StringBuilder();
    private CompletableFuture<?> completable = new CompletableFuture<>();

    public BinanceApiWebSocketListener(CountDownLatch latch, TradeEventKafkaProducer kafkaProducer) {
        this.latch = latch;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {

        builder.append(data);
        webSocket.request(1);
        if (last) {
            try {
                String messageJson = builder.toString();
                TradeEvent tradeEvent = mapper.readValue(messageJson, TradeEvent.class);
                LOGGER.info("새로운 거래가 발생:\n" + messageJson);
                if (tradeEvent.getEvent().equals("updated")) {
                    ZonedDateTime zdt = ZonedDateTime.parse(tradeEvent.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDateTime localDateTime = LocalDateTime.of(zdt.getYear(), zdt.getMonth(), zdt.getDayOfMonth(), zdt.getHour(), zdt.getMinute());
                    String timeString = localDateTime.toString();
                    LOGGER.info("카프카 프로듀서로 아이템 하나를 전송합니다.");
                    kafkaProducer.send(timeString, tradeEvent);
                } else {
                    LOGGER.info("새로운 이벤트 발생: " + messageJson);
                }

                builder = new StringBuilder();
                completable.complete(null);
                CompletionStage<?> completionStage = completable;
                completable = new CompletableFuture<>();
                return completionStage;
            } catch (JsonProcessingException e) {
                log.error("직렬화에 실패했습니다. 사유 : " + e);
            }
        }
        return completable;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        LOGGER.info("🥕🥕🥕🥕웹소켓 연결 성공🥕🥕🥕🥕");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        LOGGER.info("웹소켓이 종료되었습니다. 사유 : : " + reason + " | 상태 코드: " + statusCode);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        LOGGER.error("웹소켓 연결 실패🥕🥕🥕🥕 사유 : ", error);
        latch.countDown();
    }
}
