package com.example.track.application;

import com.example.global.common.CommonKafkaProducer;
import com.example.global.common.SignalType;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.track.dao.MoveAverageRepository;
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
    private final TradeTestKafkaProducer kafkaProducer = new CommonKafkaProducer().createTradeEventKafkaProducer();

    @Override
    @Cacheable("latestMoveAverage")
    public BigDecimal getLatestMoveAverage() {
        return moveAverageRepository.findTopByOrderByCreatedAtDesc().getMoveAverage();
    }

    @Override
    @CircuitBreaker(name = "messageService", fallbackMethod = "fallbackMessageService")
    public void processTradeEvent(TradeEvent tradeEvent) {
        CompletableFuture<BigDecimal> latestPriceFuture = fetchLatestMoveAverageAsync();
        latestPriceFuture.thenAcceptAsync(latestPrice -> {
            sendSignalBasedOnPriceComparison(tradeEvent, latestPrice);
        });
    }

    private CompletableFuture<BigDecimal> fetchLatestMoveAverageAsync() {
        return CompletableFuture.supplyAsync(this::getLatestMoveAverage);
    }

    private void sendSignalBasedOnPriceComparison(TradeEvent tradeEvent, BigDecimal latestPrice) {
        BigDecimal tradePrice = new BigDecimal(tradeEvent.getPrice());
        int comparisonResult = tradePrice.compareTo(latestPrice);

        if (comparisonResult > 0) {
            sendSignal(tradeEvent.getSeqnum(), SignalType.CURRENT_HIGHER_THAN_LAST);
        } else if (comparisonResult < 0) {
            sendSignal(tradeEvent.getSeqnum(), SignalType.CURRENT_LOWER_THAN_LAST);
        }
    }

    private void sendSignal(String seqnum, SignalType signalType) {
        kafkaProducer.send(seqnum, signalType.getValue());
    }

    public void fallbackMessageService(Throwable throwable) {
        log.error("Fallback occurred for test", throwable);
        throw new WhaleException(WhaleExceptionType.INTERNAL_SERVER_ERROR);
    }
}
