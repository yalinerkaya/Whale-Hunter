package com.example.track.application;

import com.example.track.domain.ClosePrice;
import com.example.track.dto.ClosePriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.global.config
 * fileName       : ScheduleConfig
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */

@Service
@EnableScheduling
@Slf4j
public class ScheduleService {
    private TrackService trackService;

    @Autowired
    public ScheduleService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Scheduled(cron = "0 46 18 * * ?") // 서버의 시간대에 맞추어서 설정
    @Async // 별도의 스레드에서 실행
    public void trackMoveAverageAdd() throws Exception {
        List<ClosePrice> closePrices = trackService.selectClosePriceList();
        trackService.insertMoveAverage(closePrices);
    }

    @Scheduled(cron = "0 45 18 * * ?") // 서버의 시간대에 맞추어서 설정
    @Async // 별도의 스레드에서 실행
    public void trackClosePriceAdd() throws Exception {
        ClosePriceResponse closePriceResponses = trackService.selectBinanceClosePrice();
        trackService.insertClosePrice(closePriceResponses);
    }
}
