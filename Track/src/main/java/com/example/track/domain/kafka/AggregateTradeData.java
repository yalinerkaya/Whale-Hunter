package com.example.track.domain.kafka;

/**
 * packageName    : com.example.track.kafka
 * fileName       : AggregateTradeData
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
public class AggregateTradeData {
    private String time;
    private double aggregateSales;
    private double aggregateBuys;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAggregateSales() {
        return aggregateSales;
    }

    public void setAggregateSales(double aggregateSales) {
        this.aggregateSales = aggregateSales;
    }

    public double getAggregateBuys() {
        return aggregateBuys;
    }

    public void setAggregateBuys(double aggregateBuys) {
        this.aggregateBuys = aggregateBuys;
    }
}
