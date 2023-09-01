package com.example.track.kafka;

import com.example.track.application.TrackSignalServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BinanceApiWebSocketListener implements WebSocket.Listener {
    private final CountDownLatch latch;
    private final TradeEventKafkaProducer kafkaProducer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final TrackSignalServiceImpl trackSignalServiceImpl;
    private CompletableFuture<?> completable = new CompletableFuture<>();

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {



        webSocket.request(1);

        if (!last) {
            return completable;
        }

        try {
            StringBuilder builder = new StringBuilder(data);
            String messageJson = builder.toString();
            TradeEvent tradeEvent = mapper.readValue(messageJson, TradeEvent.class);

            if (tradeEvent.getPrice() != null) {
                CompletableFuture.runAsync(() -> {trackSignalServiceImpl.processTradeEvent(tradeEvent);});
            }

            // 모든 거래 시각화 제외
/*            if (tradeEvent.getEvent().equals("updated")) {
                CompletableFuture.runAsync(() -> kafkaProducer.send(convertTimestampToTimeString(tradeEvent.getTimestamp()), tradeEvent))
                        .whenComplete((result, ex) -> {
                            if (ex != null) {
                                log.error("전송 중 에러 발생: " + ex.getMessage());
                            }
                        });
            }*/

            log.info("새로운 이벤트 발생: " + messageJson);

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
        log.info("웹소켓 연결 성공");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        log.info("웹소켓이 종료되었습니다. 사유 : : " + reason + " | 상태 코드: " + statusCode);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        log.error("웹소켓 연결 실패 사유 : ", error);
        latch.countDown();
    }
}
