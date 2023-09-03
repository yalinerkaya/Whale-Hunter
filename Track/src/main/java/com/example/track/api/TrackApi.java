package com.example.track.api;

import com.example.global.common.CommonResponse;
import com.example.track.application.TrackService;
import com.example.track.application.TrackSignalService;
import com.example.track.domain.ClosePrice;
import com.example.track.dto.ClosePriceResponse;
import com.example.track.kafka.TradeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * packageName    : com.example.track.api
 * fileName       : TrackApi
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-23        Jay       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/track")
public class TrackApi {
    private final TrackService trackService;
    private final TrackSignalService trackSignalService;

    /**
     * BTC 50일 종가 리스트 저장
     *
     * @throws Exception
     */
    @PostMapping("/close")
    public CommonResponse<Void> trackClosePriceListAdd() throws Exception {
        List<ClosePriceResponse> closePriceResponses = trackService.selectBinanceClosePriceList();
        trackService.insertClosePriceList(closePriceResponses);
        return new CommonResponse<>();
    }

    /**
     * BTC 50일 이동평균선 저장
     *
     * @throws Exception
     */
    @PostMapping("/average")
    public CommonResponse<Void> trackMoveAverageAdd() throws Exception {
        List<ClosePrice> closePrices = trackService.selectClosePriceList();
        trackService.insertMoveAverage(closePrices);
        return new CommonResponse<>();
    }

    /**
     * BTC 현재 종가와 이동평균선 비교후 상태 저장
     *
     * @throws Exception
     */
    @PostMapping("/status")
    public CommonResponse<Void> trackCompareStatus() throws Exception {
        trackService.insertBTCStatus();
        return new CommonResponse<>();
    }

    /**
     * 새로운 종가 저장
     *
     * @throws Exception
     */
    @PostMapping("/new-close")
    public CommonResponse<Void> trackClosePriceAdd() throws Exception {
        ClosePriceResponse closePriceResponses = trackService.selectBinanceClosePrice();
        trackService.insertClosePrice(closePriceResponses);
        return new CommonResponse<>();
    }

    @PostMapping("/test")
    public void test() throws Exception {
        Random random = new Random();
        String traceUid = String.valueOf(random.nextInt(900) + 100);
        TradeEvent tradeEvent = new TradeEvent("1", null, null, null, null, null, null ,null, "100", null, traceUid, null);
        trackSignalService.processTradeEvent(tradeEvent);
    }
}

