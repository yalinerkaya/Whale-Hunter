package com.example.whalehunter.controller;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.example.whalehunter.config.BinanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CoinController {

    private BinanceConfig binanceConfig;

    @Autowired
    public CoinController(
            BinanceConfig binanceConfig) {
        this.binanceConfig = binanceConfig;
    }

    @GetMapping("/ping")
    public void pingTest() throws Exception {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binanceConfig.getApiKey(), binanceConfig.getSecretKey());
        BinanceApiRestClient client = factory.newRestClient();
        client.ping();

        long serverTime = client.getServerTime();
        System.out.println(serverTime);
    }
}
