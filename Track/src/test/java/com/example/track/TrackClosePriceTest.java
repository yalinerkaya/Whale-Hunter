package com.example.track;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.example.global.config.BinanceConfig;
import com.example.track.application.TrackService;
import com.example.track.application.TrackServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.domain.Binance;
import com.example.track.dto.ClosePriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.binance.api.client.domain.market.CandlestickInterval.DAILY;
import static com.example.global.util.TrackConstants.FIVE_HUNDRED_LIMIT;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * packageName    : com.example.track
 * fileName       : TrackClosePriceTest
 * author         : 정재윤
 * date           : 2023-07-24
 * description    : 종가를 조회, 저장중 발생 가능한 경우의 수 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */

@SpringBootTest
@TestPropertySource(properties = {
        "binance.apiKey=test_api_key",
        "binance.secretKey=test_secret_key"
})
@DisplayName("종가 테스트")
class TrackClosePriceTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BinanceConfig binanceConfig() {
            return new BinanceConfig();
        }
    }

    @Autowired
    private BinanceConfig binanceConfig;

    @Mock
    private ClosePriceRepository closePriceRepository;

    @InjectMocks
    private TrackService trackService = new TrackServiceImpl(closePriceRepository, null);
    private static final String BTC_USDT = "BTCUSDT";

    @Nested
    @DisplayName("바이낸스 API 테스트")
    class testBinanceClient {
        @Test
        @DisplayName("바이낸스 Key 호출 성공")
        void testBinanceClientCreation() throws Exception {
            // Given
            assertEquals("test_api_key", binanceConfig.getApiKey());
            assertEquals("test_secret_key", binanceConfig.getSecretKey());

            // When
            Binance binance = binanceConfig.binanceClient();
            assertNotNull(binance);

            // Then
            assertEquals("test_api_key", binance.getApiKey());
            assertEquals("test_secret_key", binance.getSecretKey());
        }

        @Test
        @DisplayName("바이낸스 API 연결 성공")
        void testBinanceClientConnection() throws Exception {
            // Given
            BinanceConfig binanceConfig = mock(BinanceConfig.class);
            when(binanceConfig.getApiKey()).thenReturn("your_api_key");
            when(binanceConfig.getSecretKey()).thenReturn("your_secret_key");

            // When
            Binance binance = new BinanceConfig().binanceClient();
            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binance.getApiKey(), binance.getSecretKey());
            BinanceApiRestClient client = factory.newRestClient();

            // Then
            assertNotNull(client);
        }

        @Test
        @DisplayName("BTC 50일치 종가 리스트 반환")
        void testBinanceBTCClosePriceList() throws Exception {
            // Given
            Binance binance = new BinanceConfig().binanceClient();
            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binance.getApiKey(), binance.getSecretKey());
            BinanceApiRestClient client = factory.newRestClient();

            // When
            List<Candlestick> candlesticks = client.getCandlestickBars(BTC_USDT, DAILY, FIVE_HUNDRED_LIMIT, 50L, 0L);

            // Then
            assertNotNull(candlesticks);
            assertThat(candlesticks).hasSize(50);
        }
    }

    @Nested
    @DisplayName("종가 리스트 테스트")
    class InsertClosePriceTest {
        @Test
        @DisplayName("종가 리스트 저장 성공")
        void testInsertClosePriceList() throws Exception {
            // Given
            List<ClosePriceResponse> closePriceResponses = new ArrayList<>();
            closePriceResponses.add(new ClosePriceResponse("TEST", BigDecimal.valueOf(10000), LocalDateTime.now()));
            closePriceResponses.add(new ClosePriceResponse("TEST", BigDecimal.valueOf(11000), LocalDateTime.now()));

            // When
            trackService.insertClosePriceList(closePriceResponses);

            // Then
            verify(closePriceRepository, times(1)).saveAll(anyList());
        }

        @Test
        @DisplayName("최신 종가 업데이트")
        void testUpdateClosePriceList() throws Exception {
            // Given
            ClosePriceResponse newClosePrice = new ClosePriceResponse("TEST", BigDecimal.valueOf(100), LocalDateTime.now());

            // When
            trackService.insertClosePriceList(Arrays.asList(newClosePrice));

            // Then
            verify(closePriceRepository).saveAll(anyList());
        }
    }
}
