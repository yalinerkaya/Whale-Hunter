package com.example.whalehunter.service.coin.impl;

import com.example.whalehunter.model.coin.CoinData;
import com.example.whalehunter.service.coin.CoinService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    private List<CoinData> filteredCoinDataList = new ArrayList<>();

    @Override
    public List<CoinData> selectTop3coins() {
        List<CoinData> top3Coins = new ArrayList<>();
        if (filteredCoinDataList.size() == 0) {
            return top3Coins;
        }

        List<CoinData> sortedCoinDataList = new ArrayList<>(filteredCoinDataList);
        sortedCoinDataList.sort(Comparator.comparingDouble(CoinData::getPriceChangePercent).reversed());

        int count = 0;

        for (CoinData coinData : sortedCoinDataList) {
            if (coinData.getVolume() >= coinData.getPrevDayVolume() * 1.5 && coinData.getPriceChangePercent() > 0) {
                top3Coins.add(coinData);
                count++;
            }
            if (count == 3) {
                break;
            }
        }

        return top3Coins;
    }

    @PostConstruct
    public void init() {
/*        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiWebSocketClient webSocketClient = factory.newWebSocketClient();

        // subscribe to klines for all symbols
        webSocketClient.onKlineEvent(symbol -> {
            double volume = Double.parseDouble(symbol.getVolume());
            double prevDayVolume = Double.parseDouble(symbol.getPrevDay());
            double volumeChangePercent = (volume - prevDayVolume) / prevDayVolume * 100;

            long timestamp = symbol.getCloseTime();
            String symbolName = symbol.getSymbol();
            double price = Double.parseDouble(symbol.getClose());
            double prevClosePrice = Double.parseDouble(symbol.getPrevClose());
            double priceChangePercent = (price - prevClosePrice) / prevClosePrice * 100;

            CoinData coinData = new CoinData();
            coinData.setSymbol(symbolName);
            coinData.setPrice(price);
            coinData.setPrevClosePrice(prevClosePrice);
            coinData.setVolume(volume);
            coinData.setPrevDayVolume(prevDayVolume);
            coinData.setTimestamp(timestamp);
            coinData.setPriceChangePercent(priceChangePercent);

            synchronized (this) {
                filteredCoinDataList.add(coinData);
            }
        }, KlineInterval.ONE_MINUTE);*/
    }
}
