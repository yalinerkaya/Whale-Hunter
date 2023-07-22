package com.example.whalehunter.domain.track.application;

import com.example.whalehunter.domain.track.domain.CoinData;

import java.util.List;

public interface TrackService {
    List<CoinData> selectTop3coins() throws Exception;
}
