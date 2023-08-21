package com.example.track.kafka;

import com.example.global.config.KafkaConfig;
import com.example.track.domain.kafka.AggregateTradeData;
import com.example.track.domain.kafka.TradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * packageName    : com.example.track.kafka
 * fileName       : StreamProcessor
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Component
@Slf4j
public class StreamProcessor {
    private static final Serde<TradeEvent> TRADE_DATA_SERDE = Serdes.serdeFrom(new TradeEventSerde(), new TradeEventSerde());
    private static final Serde<AggregateTradeData> TRADE_AGGREGATE_SERDE = Serdes.serdeFrom(new AggregateTradeDataSerde(), new AggregateTradeDataSerde());
    private static final Serde<String> STRING_SERDE = Serdes.String();
    private static final String SIDE_BUY = "buy";
    private static final String SIDE_SELL = "sell";
    private static final String STATE_DIR = "/tmp/kafka-streams";
    private static final Aggregator<String, TradeEvent, AggregateTradeData> AGGREGATOR = ((key, tradeEvent, aggregate) -> {
        if (aggregate == null) {
            aggregate = new AggregateTradeData();
        }

        if (tradeEvent.getSide() == null) {
            return aggregate;
        }

        double qty = Double.parseDouble(tradeEvent.getQty());

        if (tradeEvent.getSide().equals(SIDE_BUY)) {
            aggregate.setAggregateBuys(aggregate.getAggregateBuys() + qty);
        } else if (tradeEvent.getSide().equals(SIDE_SELL)) {
            aggregate.setAggregateSales(aggregate.getAggregateSales() + qty);
        }

        aggregate.setTime(key);
        return aggregate;
    });

    private final KafkaConfig kafkaConfig;

    @Autowired
    public StreamProcessor(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    public void startKafkaStreams() {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, TradeEvent> source = builder.stream(kafkaConfig.getInputTopic(), Consumed.with(STRING_SERDE, TRADE_DATA_SERDE));
        KGroupedStream<String, TradeEvent> groupedStream = source.groupByKey();
        KTable<String, AggregateTradeData> aggregateData = groupedStream.aggregate(AggregateTradeData::new, AGGREGATOR, Materialized.with(STRING_SERDE, TRADE_AGGREGATE_SERDE));

        aggregateData.toStream().to(kafkaConfig.getOutputTopic(), Produced.with(STRING_SERDE, TRADE_AGGREGATE_SERDE));

        Topology topology = builder.build();

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getProcessor());
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServers());
        config.put(StreamsConfig.STATE_DIR_CONFIG, STATE_DIR);

        KafkaStreams streams = new KafkaStreams(topology, config);
        streams.setUncaughtExceptionHandler((thread, throwable) -> log.error(throwable.getMessage()));
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
