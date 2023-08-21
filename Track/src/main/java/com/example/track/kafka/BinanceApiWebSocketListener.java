package com.example.track.kafka;

import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.domain.kafka.TradeEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

import static com.example.global.util.DateUtils.convertTimestampToTimeString;

/**
 * packageName    : com.example.track.kafka
 * fileName       : BinanceApiWebSocketListener
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Slf4j
public class BinanceApiWebSocketListener implements WebSocket.Listener {
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
            TradeEvent tradeEvent = mapper.readValue(messageJson, TradeEvent.class);
            log.info("새로운 거래가 발생:\n" + messageJson);

            if (tradeEvent.getPrice() != null) {
                CompletableFuture<Void> processFuture = CompletableFuture.runAsync(() -> {
                    trackSignalServiceImpl.processTradeEvent(tradeEvent);
                });
            }

            if (tradeEvent.getEvent().equals("updated")) {
                CompletableFuture.runAsync(() -> kafkaProducer.send(convertTimestampToTimeString(tradeEvent.getTimestamp()), tradeEvent))
                        .whenComplete((result, ex) -> {
                            if (ex != null) {
                                log.error("전송 중 에러 발생: " + ex.getMessage());
                            }
                        });
            } else {
                log.info("새로운 이벤트 발생: " + messageJson);
            }

/*            if (tradeEvent.getEvent().equals("updated")) {
                log.info("카프카 프로듀서로 아이템 하나를 전송합니다.");
                kafkaProducer.send(convertTimestampToTimeString(tradeEvent.getTimestamp()), tradeEvent);
            } else {
                log.info("새로운 이벤트 발생: " + messageJson);
            }*/

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
        log.info("🥕🥕🥕🥕웹소켓 연결 성공🥕🥕🥕🥕");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        log.info("웹소켓이 종료되었습니다. 사유 : : " + reason + " | 상태 코드: " + statusCode);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        log.error("웹소켓 연결 실패🥕🥕🥕🥕 사유 : ", error);
        latch.countDown();
    }
}
