package com.example.whalehunter.model.coin;

import lombok.Getter;
import lombok.Setter;

/**
 * CoinData.java
 *
 * @author Jay
 */
@Getter
@Setter
public class CoinData {
    private String symbol;
    private double price;
    private double prevClosePrice;
    private double priceChange;
    private double volume;
    private double prevDayVolume;
    private long timestamp;
    private double priceChangePercent;
}
