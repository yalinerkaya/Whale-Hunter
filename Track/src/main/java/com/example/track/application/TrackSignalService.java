package com.example.track.application;

import com.example.track.domain.MoveAverage;
import com.example.track.dto.MoveAverageResponse;
import com.example.track.kafka.TradeEvent;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackSignalService
 * author         : Jay
 * date           : 2023-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26        Jay       최초 생성
 */
public interface TrackSignalService {

    void processTradeEvent(TradeEvent tradeEvent) throws Exception;

    MoveAverage getLatestMoveAverage() throws Exception;
}
