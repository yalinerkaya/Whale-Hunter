package com.example.track;

import com.example.track.application.TrackService;
import com.example.track.application.TrackServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.domain.ClosePrice;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.track.service
 * fileName       : ScheduleServiceTest
 * author         : Jay
 * date           : 2023-08-30
 * description    : 00시마다 스케쥴링하여 최신 BTC 가격을 조회, 계산 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-30        Jay       최초 생성
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("스케쥴 서비스 테스트")
public class ScheduleServiceTest {
    @Mock
    private ClosePriceRepository closePriceRepository;
    @InjectMocks
    private TrackService trackService = new TrackServiceImpl(closePriceRepository, null);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("더미 이동평균값 업데이트")
    @Test
    public void testTrackMoveAverageAddTest() throws Exception {
        // Given
        List<ClosePrice> closePrices = this.fiftyDummyCandles();

        // When
        trackService.insertMoveAverage(closePrices);

        // Then
        verify(trackService, times(1)).insertMoveAverage(closePrices);
    }

    @Test
    @DisplayName("더미 종가 업데이트")
    public void testTrackClosePriceAdd() throws Exception {
        // Given
        ClosePriceResponse closePriceResponse = this.ClosePriceDummy();
        ClosePrice closePrice = ClosePrice.createFromResponse(closePriceResponse);

        // When
        closePriceRepository.save(closePrice);

        // Then
        verify(closePriceRepository, times(1)).save(closePrice);
    }

    public List<ClosePrice> fiftyDummyCandles() {
        return Stream.generate(() -> new ClosePrice("BTC", new BigDecimal(100), LocalDateTime.now()))
                .limit(50)
                .collect(Collectors.toList());
    }

    public ClosePriceResponse ClosePriceDummy() {
        return new ClosePriceResponse("BTC",new BigDecimal(100), LocalDateTime.now());
    }
}