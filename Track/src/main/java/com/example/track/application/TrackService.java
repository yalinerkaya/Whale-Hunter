package com.example.track.application;

import com.example.track.domain.ClosePrice;
import com.example.track.dto.ClosePriceResponse;

import java.util.List;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackService
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */

public interface TrackService {

    /**
     * 50일 종가 리스트
     **/
    List<ClosePriceResponse> selectBinanceClosePriceList() throws Exception;

    void insertClosePriceList(List<ClosePriceResponse> closePriceResponses) throws Exception;

    /**
     * 50일 이동 평균선
     **/
    List<ClosePrice> selectClosePriceList() throws Exception;

    void insertMoveAverage(List<ClosePrice> closePrices) throws Exception;

    /**
     * 현재 종가와 이동 평균선을 비교
     **/
    void insertBTCStatus() throws Exception;

    /**
     * 새로운 종가
     **/
    ClosePriceResponse selectBinanceClosePrice() throws Exception;

    void insertClosePrice(ClosePriceResponse closePriceResponse) throws Exception;

}
