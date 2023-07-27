package com.example.track.application;

import com.example.global.common.Result;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.MoveAverage;
import com.example.track.domain.kafka.TradeEvent;
import com.example.track.dto.CoinStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackSignalServiceImpl
 * author         : 정재윤
 * date           : 2023-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26        정재윤       최초 생성
 */
@Service
@Slf4j
public class TrackSignalServiceImpl implements TrackSignalService {

    private static final String BREAK_OUT = "http://localhost:8003/up";
    private static final String BREAK_DOWN = "http://localhost:8003/down";
    private static final String STATUS = "http://localhost:8003/status";

    private String status;

    private final MoveAverageRepository moveAverageRepository;

    @Autowired
    public TrackSignalServiceImpl(MoveAverageRepository moveAverageRepository) {
        this.moveAverageRepository = moveAverageRepository;
    }

    @Override
    public void processTradeEvent(TradeEvent tradeEvent) {
        MoveAverage latestPrice = moveAverageRepository.findTopByOrderByCreatedAtDesc();
        if (latestPrice != null && tradeEvent.getPrice() != null) {
            Double tradeEventPrice = new Double(tradeEvent.getPrice());
            Double latestPriceMoveAverage = latestPrice.getMoveAverage().doubleValue();

            Mono<Result> coinStatus = WebClient.create(STATUS).get()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
                    .acceptCharset(Charset.forName("UTF-8"))
                    .retrieve().bodyToMono(Result.class)
                    .doOnSuccessOrError((aVoid, ex) -> {});

            Result coinStatusResult = coinStatus.log().doOnSuccessOrError((aVoid, ex) -> {}).block();
            Map<String, Object> dataMap = (Map<String, Object>) coinStatusResult.getData();
            status = (String) dataMap.get("status");

            if (tradeEventPrice > latestPriceMoveAverage && status.equals("DOWN")) {
                WebClient.create(BREAK_OUT).post().retrieve().bodyToMono(String.class).doOnError(error -> {
                    log.info("전송 실패 : " + error);
                }).subscribe(responseBody -> {
                    log.info("로컬호스트 컨트롤러로부터의 응답: " + responseBody);
                });
            }

            if (tradeEventPrice < latestPriceMoveAverage && status.equals("UP")) {
                WebClient.create(BREAK_DOWN).post().retrieve().bodyToMono(String.class).doOnError(error -> {
                    log.info("전송 실패 : " + error);
                }).subscribe(responseBody -> {
                    log.info("로컬호스트 컨트롤러로부터의 응답: " + responseBody);
                });
            }
        }
    }
}
