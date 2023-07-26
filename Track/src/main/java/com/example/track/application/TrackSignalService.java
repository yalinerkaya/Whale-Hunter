package com.example.track.application;

import com.example.track.domain.kafka.TradeEvent;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackSignalService
 * author         : 정재윤
 * date           : 2023-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26        정재윤       최초 생성
 */
public interface TrackSignalService {

    void processTradeEvent(TradeEvent tradeEvent) throws Exception;
}
