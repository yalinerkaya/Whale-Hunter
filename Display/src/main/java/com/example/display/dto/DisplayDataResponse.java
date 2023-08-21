package com.example.display.dto;

import com.example.display.domain.Data;

import java.util.List;

/**
 * packageName    : com.example.display.dto
 * fileName       : DisplayDataResponse
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
public class DisplayDataResponse {
    private List<Data> sell;
    private List<Data> buy;

    public List<Data> getSell() {
        return sell;
    }

    public void setSell(List<Data> sell) {
        this.sell = sell;
    }

    public List<Data> getBuy() {
        return buy;
    }

    public void setBuy(List<Data> buy) {
        this.buy = buy;
    }
}
