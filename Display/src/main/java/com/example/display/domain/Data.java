package com.example.display.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * packageName    : com.example.display.domain
 * fileName       : Data
 * author         : Jay
 * date           : 2023-07-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-27        Jay       최초 생성
 */
public class Data {
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
