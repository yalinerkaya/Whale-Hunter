package com.example.display.application;

import com.example.display.kafka.AggregateTradeData;
import com.example.display.domain.Data;
import com.example.display.dto.DisplayDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.global.util.DisplayConstants.ONE_MINUTE;

/**
 * packageName    : com.example.display.application
 * fileName       : DisplayServiceImpl
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@RequiredArgsConstructor
@Service
public class DisplayServiceImpl implements DisplayService {

    private final KafkaConsumerService<?> kafkaConsumerService;
    private final Map<String, Data> buyDataCache = new HashMap<>(ONE_MINUTE);
    private final Map<String, Data> sellDataCache = new HashMap<>(ONE_MINUTE);

    @Override
    public Optional<DisplayDataResponse> buildDataset() {
        List<AggregateTradeData> tradeDataList = (List<AggregateTradeData>) kafkaConsumerService.poll();

        if (tradeDataList.isEmpty() && buyDataCache.isEmpty() && sellDataCache.isEmpty()) {
            return Optional.empty();
        }

        tradeDataList.forEach(tradeData -> {
            String time = tradeData.getTime();
            buyDataCache.put(time, new Data(time, tradeData.getAggregateBuys()));
            sellDataCache.put(time, new Data(time, tradeData.getAggregateSales()));
        });

        kafkaConsumerService.commitOffsets();
        return Optional.of(returnCachedDataset());
    }

    private DisplayDataResponse returnCachedDataset() {
        return new DisplayDataResponse(new ArrayList<>(sellDataCache.values()), new ArrayList<>(buyDataCache.values()));
    }
}
