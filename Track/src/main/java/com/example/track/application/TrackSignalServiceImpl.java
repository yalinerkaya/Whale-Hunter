package com.example.track.application;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.MoveAverage;
import com.example.track.domain.kafka.TradeEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

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

    private final MoveAverageRepository moveAverageRepository;
    private final MessageFeignClient messageFeignClient;

    private final String UP = "high";
    private final String DOWN = "down";

    @Autowired
    public TrackSignalServiceImpl(MoveAverageRepository moveAverageRepository, MessageFeignClient messageFeignClient) {
        this.moveAverageRepository = moveAverageRepository;
        this.messageFeignClient = messageFeignClient;
    }

    @Override
    @CircuitBreaker(name = "messageService", fallbackMethod = "fallbackMessageService")
    public void processTradeEvent(TradeEvent tradeEvent) {

        MoveAverage latestPrice = moveAverageRepository.findTopByOrderByCreatedAtDesc();

        boolean hasLatestPriceAndTradeEvent = latestPrice != null && tradeEvent.getPrice() != null;

        if (hasLatestPriceAndTradeEvent) {
            double tradeEventPrice = new Double(tradeEvent.getPrice());
            double latestPriceMoveAverage = latestPrice.getMoveAverage().doubleValue();
            String lastBtcStatus = messageFeignClient.checkBTCStatus().getBody().getData().getStatus();

            if (isPriceAboveMoveAverage(tradeEventPrice, latestPriceMoveAverage) && isStilDown(lastBtcStatus)) {
                messageFeignClient.priceBreakout();
            }

            if (isPriceBelowMoveAverage(tradeEventPrice, latestPriceMoveAverage) && isStilUp(lastBtcStatus)) {
                messageFeignClient.priceBreakdown();
            }
        }
    }

    private boolean isPriceAboveMoveAverage(double tradeEventPrice, double latestPriceMoveAverage) {
        return tradeEventPrice > latestPriceMoveAverage;
    }

    private boolean isPriceBelowMoveAverage(double tradeEventPrice, double latestPriceMoveAverage) {
        return tradeEventPrice < latestPriceMoveAverage;
    }

    private boolean isStilDown(String lastBtcStatus) {
        return DOWN.equals(lastBtcStatus);
    }

    private boolean isStilUp(String lastBtcStatus) {
        return UP.equals(lastBtcStatus);
    }

    public void fallbackMessageService(TradeEvent tradeEvent, Throwable throwable) {
        log.error("Fallback occurred for processTradeEvent = ", throwable);
        throw new WhaleException(WhaleExceptionType.INTERNAL_SERVER_ERROR);
    }
}
