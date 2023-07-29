package com.example.display.application;

import com.example.display.domain.AggregateTradeData;
import com.example.display.domain.Data;
import com.example.display.dto.DisplayDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * packageName    : com.example.display.application
 * fileName       : DisplayServiceImpl
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@Service
public class DisplayServiceImpl implements DisplayService {
    private final KafkaConsumerService<?> kafkaConsumerService;
    private final Map<String, Data> buyDataCache = new HashMap<>(60);
    private final Map<String, Data> sellDataCache = new HashMap<>(60);

    @Autowired
    public DisplayServiceImpl(KafkaConsumerService<?> kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @Override
    public Optional<DisplayDataResponse> buildDataset() {
        List<AggregateTradeData> tradeDataList = (List<AggregateTradeData>) kafkaConsumerService.poll();

        if (tradeDataList.isEmpty()) {
            if (!buyDataCache.isEmpty() && !sellDataCache.isEmpty()) {
                return Optional.of(returnCachedDataset());
            } else {
                return Optional.empty();
            }
        } else {
            tradeDataList.forEach(tradeData -> {
                Data buy = new Data();
                String timeBuy = tradeData.getTime();
                buy.setTime(timeBuy);
                buy.setValue(tradeData.getAggregateBuys());
                buyDataCache.put(timeBuy, buy);

                Data sell = new Data();
                String timeSell = tradeData.getTime();
                sell.setTime(timeSell);
                sell.setValue(tradeData.getAggregateSales());
                sellDataCache.put(timeSell, sell);
            });
            kafkaConsumerService.commitOffsets();
            return Optional.of(returnCachedDataset());
        }
    }

    private DisplayDataResponse returnCachedDataset() {
        DisplayDataResponse dataset = new DisplayDataResponse();
        dataset.setBuy(new ArrayList<>(buyDataCache.values()));
        dataset.setSell(new ArrayList<>(sellDataCache.values()));
        return dataset;
    }
}
