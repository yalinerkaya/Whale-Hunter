package com.example.track.application;

import com.example.track.domain.ClosePrice;
import com.example.track.dto.ClosePriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.global.util.TrackConstants.MIDNIGHT;
import static com.example.global.util.TrackConstants.MIDNIGHT_10_SECONDS;

/**
 * packageName    : com.example.track.application
 * fileName       : ScheduleService
 * author         : Jay
 * date           : 2023-07-24
 * description    : 매일 00시가 되면 새로운 종가와 이동평균값을 계산합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class ScheduleService {

    private final TrackService trackService;
    private final TrackSignalService trackSignalService;

    @Scheduled(cron = MIDNIGHT_10_SECONDS)
    @Async
    public void trackMoveAverageAdd() throws Exception {
        List<ClosePrice> closePrices = trackService.selectClosePriceList();
        trackService.insertMoveAverage(closePrices);
        trackService.insertBTCStatus();
        // 캐싱
        trackSignalService.getLatestMoveAverage();
    }

    @Scheduled(cron = MIDNIGHT)
    @Async
    @CacheEvict(value = "latestMoveAverage", allEntries = true)
    public void trackClosePriceAdd() throws Exception {
        ClosePriceResponse closePriceResponses = trackService.selectBinanceClosePrice();
        trackService.insertClosePrice(closePriceResponses);
    }
}
