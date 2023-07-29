package com.example.track.kafka;

import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.domain.kafka.TradeEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

import static com.example.global.util.DateUtils.convertTimestampToTimeString;

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
    private final TrackSignalServiceImpl trackSignalServiceImpl;
    private final StringBuilder builder = new StringBuilder();
    private CompletableFuture<?> completable = new CompletableFuture<>();

    public BinanceApiWebSocketListener(CountDownLatch latch, TradeEventKafkaProducer kafkaProducer, TrackSignalServiceImpl trackSignalServiceImpl) {
        this.latch = latch;
        this.kafkaProducer = kafkaProducer;
        this.trackSignalServiceImpl = trackSignalServiceImpl;
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {

        builder.append(data);
        webSocket.request(1);

        if (!last) {
            return completable;
        }

        try {
            String messageJson = builder.toString();
            ObjectMapper mapper = new ObjectMapper();
            int startIndex = 0;
            while (startIndex < messageJson.length()) {
                int endIndex = messageJson.indexOf("}", startIndex);
                if (endIndex == -1) {
                    break;
                }

                String json = messageJson.substring(startIndex, endIndex + 1);
                TradeEvent tradeEvent = mapper.readValue(json, TradeEvent.class);
                if (tradeEvent.getEvent().equals("updated")) {
                    System.out.println("카프카 프로듀서로 아이템 하나를 전송합니다. " + tradeEvent.getSide());
                    kafkaProducer.send(convertTimestampToTimeString(tradeEvent.getTimestamp()), tradeEvent);
                    trackSignalServiceImpl.processTradeEvent(tradeEvent);
                } else {
                    System.out.println("새로운 이벤트 발생: " + json);
                }

                startIndex = endIndex + 1;
            }

            completable.complete(null);
            CompletionStage<?> completionStage = completable;
            completable = new CompletableFuture<>();
            return completionStage;
        } catch (JsonProcessingException e) {
            log.error("직렬화에 실패했습니다. 사유 : " + e);
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
