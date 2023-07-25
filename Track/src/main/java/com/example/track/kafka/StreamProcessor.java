package com.example.track.kafka;

import com.example.global.exception.WhaleException;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * packageName    : com.example.track.kafka
 * fileName       : StreamProcessor
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@Component
public class StreamProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamProcessor.class);
    private static final Serde<TradeEvent> TRADE_DATA_SERDE = Serdes.serdeFrom(new TradeEventSerde(), new TradeEventSerde());
    private static final Serde<AggregateTradeData> TRADE_AGGREGATE_SERDE = Serdes.serdeFrom(new AggregateTradeDataSerde(), new AggregateTradeDataSerde());
    private static final Aggregator<String, TradeEvent, AggregateTradeData> AGGREGATOR = ((key, tradeEvent, aggregate) -> {
        if (aggregate == null) {
            aggregate = new AggregateTradeData();
            aggregate.setTime(key);
        }

        if (aggregate.getTime() == null) {
            aggregate.setTime(key);
        }

        if (tradeEvent.getSide() == null) {
            return aggregate;
        }

        if (tradeEvent.getSide().equals("buy")) {
            double bought = aggregate.getAggregateBuys();
            aggregate.setAggregateBuys(bought + Double.parseDouble(tradeEvent.getQty()));
        } else if (tradeEvent.getSide().equals("sell")) {
            double sold = aggregate.getAggregateBuys();
            aggregate.setAggregateSales(sold + Double.parseDouble(tradeEvent.getQty()));
        }
        return aggregate;
    });

    public static void main(String[] args) throws WhaleException {
        try {
            start();
        } catch (WhaleException e) {
            LOGGER.error("스트림즈 실행 실패 사유 :", e);
        }
    }

    public static final void start() {
        StreamsBuilder builder = new StreamsBuilder();
        Consumed<String, TradeEvent> consumedWith = Consumed.with(Serdes.String(), TRADE_DATA_SERDE);
        KStream<String, TradeEvent> source = builder.stream("trade_events", consumedWith);
        KGroupedStream<String, TradeEvent> groupedStream = source.groupByKey();

        KTable<String, AggregateTradeData> aggregateData = groupedStream.aggregate(AggregateTradeData::new, AGGREGATOR, Materialized.with(Serdes.String(), TRADE_AGGREGATE_SERDE));
        aggregateData.toStream().to("trade_data", Produced.with(Serdes.String(), TRADE_AGGREGATE_SERDE));
        Topology topology = builder.build();

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "crypto-data-processor");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
        config.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");
        KafkaStreams streams = new KafkaStreams(topology, config);
        streams.setUncaughtExceptionHandler((thread, throwable) -> LOGGER.error(throwable.getMessage()));
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
