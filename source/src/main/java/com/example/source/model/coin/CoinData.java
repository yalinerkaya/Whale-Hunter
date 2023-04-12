package com.example.source.model.coin;

/**
 * CoinData.java
 *
 * @author Jay
 */
public class CoinData {
    private double price = 0.0;
    private double highPrice = 0.0;
    private double volume = 0.0;
    private long lastUpdateTime = 0L;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public double getPriceChangePercent() {
        return (price - highPrice) / highPrice;
    }

    public boolean isPriceRising() {
        return price >= highPrice;
    }
}
