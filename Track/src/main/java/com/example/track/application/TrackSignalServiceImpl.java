package com.example.track.application;

import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.MoveAverage;
import com.example.track.domain.kafka.TradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

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

    private static final String LOCALHOST_ENDPOINT = "http://localhost:8003/send";

    private final MoveAverageRepository moveAverageRepository;

    @Autowired
    public TrackSignalServiceImpl(MoveAverageRepository moveAverageRepository) {
        this.moveAverageRepository = moveAverageRepository;
    }

    @Override
    public void processTradeEvent(TradeEvent tradeEvent) {
        MoveAverage latestPrice = moveAverageRepository.findTopByOrderByCreatedAtDesc();
        if (latestPrice != null && tradeEvent.getPrice() != null) {
            BigDecimal tradeEventPrice = new BigDecimal(tradeEvent.getPrice());
            BigDecimal latestPriceMoveAverage = latestPrice.getMoveAverage();

            if (tradeEventPrice.compareTo(latestPriceMoveAverage) == 0) {
                WebClient.create(LOCALHOST_ENDPOINT).post().retrieve().bodyToMono(String.class).doOnError(error -> {
                    log.info("전송 실패 : " + error);
                }).subscribe(responseBody -> {
                    log.info("로컬호스트 컨트롤러로부터의 응답: " + responseBody);
                });
            }
        }
    }
}
