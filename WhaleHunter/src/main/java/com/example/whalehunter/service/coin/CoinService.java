package com.example.whalehunter.service.coin;

import com.example.whalehunter.model.coin.CoinData;

import java.util.List;

public interface CoinService {
    List<CoinData> selectTop3coins() throws Exception;
}
