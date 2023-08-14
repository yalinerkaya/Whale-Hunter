package com.example.track.application;

import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.MoveAverage;
import com.example.track.domain.kafka.TradeEvent;
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

    @Autowired
    public TrackSignalServiceImpl(MoveAverageRepository moveAverageRepository, MessageFeignClient messageFeignClient) {
        this.moveAverageRepository = moveAverageRepository;
        this.messageFeignClient = messageFeignClient;
    }

    @Override
    public void processTradeEvent(TradeEvent tradeEvent) {
        MoveAverage latestPrice = moveAverageRepository.findTopByOrderByCreatedAtDesc();
        if (latestPrice != null && tradeEvent.getPrice() != null) {
            Double tradeEventPrice = new Double(tradeEvent.getPrice());
            Double latestPriceMoveAverage = latestPrice.getMoveAverage().doubleValue();

            String status = messageFeignClient.checkBTCStatus().getBody().getData().getStatus();

            if (tradeEventPrice > latestPriceMoveAverage && status.equals("DOWN")) {
                messageFeignClient.priceBreakout();
            }

            if (tradeEventPrice < latestPriceMoveAverage && status.equals("UP")) {
                messageFeignClient.priceBreakdown();
            }
        }
    }
}
