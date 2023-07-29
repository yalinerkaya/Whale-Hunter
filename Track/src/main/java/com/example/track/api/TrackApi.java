package com.example.track.api;

import com.example.global.common.CommonResponse;
import com.example.track.application.TrackService;
import com.example.track.domain.ClosePrice;
import com.example.track.domain.kafka.TradeEvent;
import com.example.track.dto.ClosePriceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RequestMapping("/track")
public class TrackApi {
    private final TrackService trackService;

    @Autowired
    public TrackApi(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * BTC 50일 종가 리스트 저장
     *
     * @throws Exception
     */
    @GetMapping("/close")
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
    @GetMapping("/average")
    public CommonResponse<Void> trackMoveAverageAdd() throws Exception {
        List<ClosePrice> closePrices = trackService.selectClosePriceList();
        trackService.insertMoveAverage(closePrices);
        return new CommonResponse<>();
    }

    /**
     * 새로운 종가 저장
     *
     * @throws Exception
     */
    @GetMapping("/new-close")
    public CommonResponse<Void> trackClosePriceAdd() throws Exception {
        ClosePriceResponse closePriceResponses = trackService.selectBinanceClosePrice();
        trackService.insertClosePrice(closePriceResponses);
        return new CommonResponse<>();
    }

    @GetMapping("/test")
    public void test() throws JsonProcessingException {
        String jsonString = "{\"seqnum\":0,\"event\":\"subscribed\",\"channel\":\"trades\",\"symbol\":\"BTC-USD\"}" +
                "{\"seqnum\":1,\"event\":\"updated\",\"channel\":\"trades\",\"symbol\":\"BTC-USD\",\"timestamp\":\"2023-07-29T14:28:08.845239Z\",\"side\":\"sell\",\"qty\":0.02513559,\"price\":29323.15,\"trade_id\":\"844437824652852\"}";

        // 문자열에서 각 JSON 객체를 찾아서 파싱합니다.
        ObjectMapper mapper = new ObjectMapper();
        int startIndex = 0;
        while (startIndex < jsonString.length()) {
            int endIndex = jsonString.indexOf("}", startIndex);
            if (endIndex == -1) {
                break;
            }

            String json = jsonString.substring(startIndex, endIndex + 1);
            TradeEvent tradeEvent = mapper.readValue(json, TradeEvent.class);
            if (tradeEvent.getEvent().equals("updated")) {
                System.out.println("카프카 프로듀서로 아이템 하나를 전송합니다.");
                // kafkaProducer.send(convertTimestampToTimeString(tradeEvent.getTimestamp()), tradeEvent);
            } else {
                System.out.println("새로운 이벤트 발생: " + json);
            }

            startIndex = endIndex + 1;
        }
    }

}
