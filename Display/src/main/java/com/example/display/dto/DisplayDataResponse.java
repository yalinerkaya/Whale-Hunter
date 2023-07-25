package com.example.display.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * packageName    : com.example.display.dto
 * fileName       : DisplayDataResponse
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
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

    public static class Data {

        @JsonProperty("x")
        private String time;

        @JsonProperty("y")
        private double value;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
