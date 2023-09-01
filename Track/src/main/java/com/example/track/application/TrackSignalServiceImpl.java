package com.example.track.application;

import com.example.global.common.SignalType;
import com.example.global.config.KafkaConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.MoveAverage;
import com.example.track.dto.MoveAverageResponse;
import com.example.track.kafka.Kafka;
import com.example.track.kafka.TradeEvent;
import com.example.track.kafka.TradeTestKafkaProducer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackSignalServiceImpl
 * author         : Jay
 * date           : 2023-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26        Jay       최초 생성
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TrackSignalServiceImpl implements TrackSignalService {
    private final MoveAverageRepository moveAverageRepository;
    private final TradeTestKafkaProducer kafkaCommonProduce;

    @Override
    @Cacheable("latestMoveAverage")
    public MoveAverage getLatestMoveAverage() {
        return moveAverageRepository.findOneByOrderByCreatedAtDesc();
    }

    @Override
    @CircuitBreaker(name = "messageService", fallbackMethod = "fallbackMessageService")
    public void processTradeEvent(TradeEvent tradeEvent) {
        CompletableFuture<MoveAverage> latestMoveAverageFuture = CompletableFuture.supplyAsync(() -> {
            return getLatestMoveAverage();
        });

        latestMoveAverageFuture.thenAcceptAsync(latestPrice -> {
            sendSignalBasedOnPriceComparison(tradeEvent, latestPrice);
        }).join();
    }

    private void sendSignalBasedOnPriceComparison(TradeEvent tradeEvent, MoveAverage moveAverageResponse) {
        BigDecimal tradePrice = new BigDecimal(tradeEvent.getPrice());
        int comparisonResult = tradePrice.compareTo(moveAverageResponse.getMoveAverage());

        // Breakout
        if (comparisonResult > 0 && moveAverageResponse.getLastStatus().equals(SignalType.CURRENT_LOWER_THAN_LAST.getValue())) {
            kafkaCommonProduce.send(tradeEvent.getSeqnum(), SignalType.CURRENT_HIGHER_THAN_LAST.getValue());
            moveAverageRepository.save(moveAverageResponse.moveAverageUp(moveAverageResponse));
        }

        // Breakdown
        if (comparisonResult < 0 && moveAverageResponse.getLastStatus().equals(SignalType.CURRENT_HIGHER_THAN_LAST.getValue())) {
            kafkaCommonProduce.send(tradeEvent.getSeqnum(), SignalType.CURRENT_LOWER_THAN_LAST.getValue());
            moveAverageRepository.save(moveAverageResponse.moveAverageDown(moveAverageResponse));
        }
    }

    public void fallbackMessageService(Throwable throwable) {
        log.error("Fallback occurred for test", throwable);
        throw new WhaleException(WhaleExceptionType.INTERNAL_SERVER_ERROR);
    }
}
