package com.example.track.application;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.example.global.config.BinanceConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.Binance;
import com.example.track.domain.ClosePrice;
import com.example.track.domain.MoveAverage;
import com.example.track.dto.ClosePriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.binance.api.client.domain.market.CandlestickInterval.DAILY;
import static com.example.global.util.TrackConstants.*;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackServiceImpl
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */

@Service
public class TrackServiceImpl implements TrackService {

    private ClosePriceRepository closePriceRepository;
    private MoveAverageRepository moveAverageRepository;

    @Autowired
    public TrackServiceImpl(
            ClosePriceRepository closePriceRepository,
            MoveAverageRepository moveAverageRepository) {
        this.closePriceRepository = closePriceRepository;
        this.moveAverageRepository = moveAverageRepository;
    }

    @Override
    public List<ClosePriceResponse> selectBinanceClosePriceList() throws Exception {
        Binance binance = new BinanceConfig().binanceClient();
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binance.getApiKey(), binance.getSecretKey());
        BinanceApiRestClient client = factory.newRestClient();

        long endTime = Instant.now().truncatedTo(ChronoUnit.DAYS).minus(ONE, ChronoUnit.DAYS).toEpochMilli();
        long startTime = Instant.now().truncatedTo(ChronoUnit.DAYS).minus(FIFTY, ChronoUnit.DAYS).toEpochMilli();

        List<ClosePriceResponse> closePriceResponses = new ArrayList<>();
        List<Candlestick> candlesticks = client.getCandlestickBars(BTC_USDT, DAILY, 500, startTime, endTime);

        for (Candlestick candlestick : candlesticks) {
            LocalDateTime closeTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getCloseTime()), ZoneId.of(UTC));
            BigDecimal closePrice = new BigDecimal(candlestick.getClose());
            closePriceResponses.add(new ClosePriceResponse(BTC_USDT, closePrice, closeTime));
        }

        return closePriceResponses;
    }

    @Override
    @Transactional
    public void insertClosePriceList(List<ClosePriceResponse> closePriceResponses) throws Exception {
        List<ClosePrice> closePrices = closePriceResponses.stream()
            .map(response -> new ClosePrice(response.getSymbol(), response.getClosingPrice(), response.getClosedAt()))
            .collect(Collectors.toList());

        closePriceRepository.saveAll(closePrices);
    }

    @Override
    public List<ClosePrice> selectClosePriceList() throws Exception {
        List<ClosePrice> allClosePrices = closePriceRepository.findAllByOrderByClosedAtDesc();
        List<ClosePrice> latest50ClosePrices = allClosePrices.stream().limit(50).collect(Collectors.toList());
        return latest50ClosePrices;
    }

    @Override
    public void insertMoveAverage(List<ClosePrice> closePrices) throws Exception {
        if (closePrices == null || closePrices.isEmpty() || closePrices.size() < FIFTY) {
            throw new WhaleException(WhaleExceptionType.TRACK_INVALID_QUANTITY);
        }

        BigDecimal movingAverageValue = BigDecimal.ZERO;

        for (int i = ZERO; i < FIFTY; i++) {
            movingAverageValue = movingAverageValue.add(closePrices.get(i).getClosingPrice());
        }

        movingAverageValue = movingAverageValue.divide(BigDecimal.valueOf(FIFTY), TWO_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
        ClosePrice recentClosePrice = closePrices.get(ZERO);
        MoveAverage moveAverage = new MoveAverage(recentClosePrice.getClosePriceUid(),BTC_USDT, movingAverageValue, recentClosePrice.getClosedAt());
        moveAverageRepository.save(moveAverage);
    }

    @Override
    public ClosePriceResponse selectBinanceClosePrice() throws Exception {
        Binance binance = new BinanceConfig().binanceClient();
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binance.getApiKey(), binance.getSecretKey());
        BinanceApiRestClient client = factory.newRestClient();

        long endTime = Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli();
        long startTime = Instant.now().truncatedTo(ChronoUnit.DAYS).minus(ONE, ChronoUnit.DAYS).toEpochMilli();

        Candlestick candlestick = client.getCandlestickBars(BTC_USDT, DAILY, 1, startTime, endTime).get(0);

        LocalDateTime closeTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getCloseTime()), ZoneId.of(UTC));
        BigDecimal closePrice = new BigDecimal(candlestick.getClose());

        return new ClosePriceResponse(BTC_USDT, closePrice, closeTime);
    }

    @Override
    @Transactional
    public void insertClosePrice(ClosePriceResponse closePriceResponse) throws Exception {
        closePriceRepository.save(new ClosePrice(closePriceResponse.getSymbol(), closePriceResponse.getClosingPrice(), closePriceResponse.getClosedAt()));
    }
}
