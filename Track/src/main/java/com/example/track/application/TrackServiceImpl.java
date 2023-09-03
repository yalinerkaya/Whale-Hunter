package com.example.track.application;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.example.global.common.SignalType;
import com.example.global.config.BinanceConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.global.util.DateUtils;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.Binance;
import com.example.track.domain.ClosePrice;
import com.example.track.domain.MoveAverage;
import com.example.track.dto.ClosePriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.binance.api.client.domain.market.CandlestickInterval.DAILY;
import static com.example.global.util.TrackConstants.*;

/**
 * packageName    : com.example.track.application
 * fileName       : TrackServiceImpl
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */

@Service
public class TrackServiceImpl implements TrackService {

    private final ClosePriceRepository closePriceRepository;
    private final MoveAverageRepository moveAverageRepository;
    private final BinanceApiRestClient client;
    @Autowired
    public TrackServiceImpl(ClosePriceRepository closePriceRepository, MoveAverageRepository moveAverageRepository) {
        this.closePriceRepository = closePriceRepository;
        this.moveAverageRepository = moveAverageRepository;
        Binance binance = new BinanceConfig().binanceClient();
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binance.getApiKey(), binance.getSecretKey());
        this.client = factory.newRestClient();
    }

    @Override
    public List<ClosePriceResponse> selectBinanceClosePriceList() throws Exception {
        long endTime = DateUtils.getTodayStartTimeInMillis();
        long startTime = DateUtils.getStartTimeBeforeDays(FIFTY);

        List<ClosePriceResponse> closePriceResponses = new ArrayList<>();
        List<Candlestick> candlesticks = client.getCandlestickBars(BTC_USDT, DAILY, FIVE_HUNDRED_LIMIT, startTime, endTime);

        if(CollectionUtils.isEmpty(candlesticks)){
            throw new WhaleException(WhaleExceptionType.TRACK_REQUIRED_CANDLESTICK);
        }

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
                .map(ClosePrice::createFromResponse)
                .collect(Collectors.toList());

        closePriceRepository.saveAll(closePrices);
    }

    @Override
    public List<ClosePrice> selectClosePriceList() throws Exception {
        List<ClosePrice> allClosePrices = closePriceRepository.findAllByOrderByClosedAtDesc();

        if(CollectionUtils.isEmpty(allClosePrices)){
            throw new WhaleException(WhaleExceptionType.TRACK_REQUIRED_CANDLESTICK);
        }

        return allClosePrices.stream().limit(FIFTY).collect(Collectors.toList());
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

        movingAverageValue = movingAverageValue.divide(BigDecimal.valueOf(FIFTY), TWO_DECIMAL_PLACES, RoundingMode.HALF_UP);
        ClosePrice recentClosePrice = closePrices.get(ZERO);
        MoveAverage moveAverage = new MoveAverage(recentClosePrice.getClosePriceUid(), BTC_USDT, movingAverageValue, recentClosePrice.getClosedAt());
        moveAverageRepository.save(moveAverage);
    }

    @Override
    public void insertBTCStatus() throws Exception {
        ClosePrice closePrice = closePriceRepository.findFirstByOrderByClosedAtDesc();

        if(ObjectUtils.isEmpty(closePrice)){
            throw new WhaleException(WhaleExceptionType.TRACK_REQUIRED_CLOSE_PRICE);
        }

        MoveAverage moveAverage = moveAverageRepository.findOneByOrderByCreatedAtDesc();

        if(ObjectUtils.isEmpty(moveAverage)){
            throw new WhaleException(WhaleExceptionType.TRACK_REQUIRED_MOVE_AVERAGE);
        }

        int comparisonResult = closePrice.getClosingPrice().compareTo(moveAverage.getMoveAverage());

        if (comparisonResult > 0) {
            moveAverage.changeStatus(SignalType.CURRENT_HIGHER_THAN_LAST);
        }

        if (comparisonResult < 0) {
            moveAverage.changeStatus(SignalType.CURRENT_LOWER_THAN_LAST);
        }

        moveAverageRepository.save(moveAverage);
    }

    @Override
    public ClosePriceResponse selectBinanceClosePrice() throws Exception {
        long endTime = DateUtils.getTodayStartTimeInMillis();
        long startTime = DateUtils.getStartTimeBeforeDays(1);

        Candlestick candlestick = client.getCandlestickBars(BTC_USDT, DAILY, ONE, startTime, endTime).get(ZERO);

        if(ObjectUtils.isEmpty(candlestick)){
            throw new WhaleException(WhaleExceptionType.TRACK_REQUIRED_CANDLESTICK);
        }

        LocalDateTime closeTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(candlestick.getCloseTime()), ZoneId.of(UTC));
        BigDecimal closePrice = new BigDecimal(candlestick.getClose());

        return new ClosePriceResponse(BTC_USDT, closePrice, closeTime);
    }

    @Override
    @Transactional
    public void insertClosePrice(ClosePriceResponse closePriceResponse) throws Exception {
        closePriceRepository.save(ClosePrice.createFromResponse(closePriceResponse));
    }
}
