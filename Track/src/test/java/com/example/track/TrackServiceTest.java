package com.example.track;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.example.global.util.TrackConstants;
import com.example.track.application.TrackServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.ClosePrice;
import com.example.track.domain.MoveAverage;
import com.example.track.dto.ClosePriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * packageName    : com.example.track.service
 * fileName       : TrackServiceTest
 * author         : Jay
 * date           : 2023-08-30
 * description    : BTC 종가를 조회, 이동평균값 계산, 상태 저장 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-30        Jay       최초 생성
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("트랙 서비스 테스트")
public class TrackServiceTest {

    @Mock
    private ClosePriceRepository closePriceRepository;

    @Mock
    private MoveAverageRepository moveAverageRepository;

    @Mock
    private BinanceApiRestClient binanceApiRestClient;

    @InjectMocks
    private TrackServiceImpl trackService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("거래소 BTC 종가 조회")
    public void testSelectBinanceClosePriceList() throws Exception {
        // given
        List<Candlestick> candlestickList = new ArrayList<>();
        when(binanceApiRestClient.getCandlestickBars(any(), any(), any(), anyLong(), anyLong())).thenReturn(candlestickList);

        // when
        List<ClosePriceResponse> closePriceResponses = trackService.selectBinanceClosePriceList();

        // then
        assertNotNull(closePriceResponses);
    }

    @Test
    @DisplayName("종가 리스트 추가")
    public void testInsertClosePriceList() throws Exception {
        // given
        List<ClosePriceResponse> closePriceResponses = new ArrayList<>();

        List<ClosePrice> mockClosePrices = closePriceResponses.stream()
                .map(ClosePrice::createFromResponse)
                .collect(Collectors.toList());

        when(closePriceRepository.saveAll(anyList())).thenReturn(mockClosePrices);

        // when
        trackService.insertClosePriceList(closePriceResponses);

        // then
        verify(closePriceRepository).saveAll(mockClosePrices);
    }

    @Test
    @DisplayName("DB 종가 리스트 조회")
    public void testSelectClosePriceList() throws Exception {
        // given
        List<ClosePrice> mockClosePrices = new ArrayList<>();
        when(closePriceRepository.findAllByOrderByClosedAtDesc()).thenReturn(mockClosePrices);

        // when
        List<ClosePrice> result = trackService.selectClosePriceList();

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("이동평균값 추가")
    public void testInsertMoveAverage() throws Exception {
        // given
        List<ClosePrice> closePrices = new ArrayList<>();

        BigDecimal sumOfClosingPrices = closePrices.stream()
            .limit(TrackConstants.FIFTY)
            .map(ClosePrice::getClosingPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal movingAverageValue = sumOfClosingPrices
            .divide(BigDecimal.valueOf(TrackConstants.FIFTY), TrackConstants.TWO_DECIMAL_PLACES, RoundingMode.HALF_UP);

        ClosePrice recentClosePrice = closePrices.get(0);

        MoveAverage expectedMoveAverage = new MoveAverage(
                recentClosePrice.getClosePriceUid(),
                TrackConstants.BTC_USDT,
                movingAverageValue,
                recentClosePrice.getClosedAt()
        );

        when(moveAverageRepository.save(any())).thenReturn(expectedMoveAverage);

        // when
        trackService.insertMoveAverage(closePrices);

        // then
        verify(moveAverageRepository).save(expectedMoveAverage);
    }

    @Test
    @DisplayName("BTC 상태(돌파) 추가")
    public void testInsertBTCStatusHigher() throws Exception {
        // given
        ClosePrice closePrice = mock(ClosePrice.class);
        MoveAverage moveAverage = mock(MoveAverage.class);

        when(closePriceRepository.findOneByOrderByClosedAtDesc()).thenReturn(closePrice);
        when(moveAverageRepository.findOneByOrderByCreatedAtDesc()).thenReturn(moveAverage);

        // when
        trackService.insertBTCStatus();

        // then
        verify(moveAverageRepository).save(moveAverage);
    }

    @Test
    @DisplayName("BTC 상태(하락) 추가")
    public void testInsertBTCStatusLower() throws Exception {
        // given
        ClosePrice closePrice = mock(ClosePrice.class);
        MoveAverage moveAverage = mock(MoveAverage.class);

        when(closePriceRepository.findOneByOrderByClosedAtDesc()).thenReturn(closePrice);
        when(moveAverageRepository.findOneByOrderByCreatedAtDesc()).thenReturn(moveAverage);

        // when
        trackService.insertBTCStatus();

        // then
        verify(moveAverageRepository).save(moveAverage);
    }
}
