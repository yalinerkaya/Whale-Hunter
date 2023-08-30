package com.example.track.service;

import com.example.track.application.ScheduleService;
import com.example.track.application.TrackService;
import com.example.track.application.TrackSignalService;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * packageName    : com.example.track.service
 * fileName       : ScheduleServiceTest
 * author         : Jay
 * date           : 2023-08-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-30        Jay       최초 생성
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("스케쥴 서비스 테스트")
public class ScheduleServiceTest {

    @Mock
    private TrackService trackService;

    @Mock
    private TrackSignalService trackSignalService;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("최신 이동평균값 업데이트")
    public void testTrackMoveAverageAdd() throws Exception {
        // Given
        List<ClosePrice> closePrices = new ArrayList<>();
        when(trackService.selectClosePriceList()).thenReturn(closePrices);

        // When
        scheduleService.trackMoveAverageAdd();

        // Then
        verify(trackService).insertMoveAverage(closePrices);
        verify(trackService).insertBTCStatus();
        verify(trackSignalService).getLatestMoveAverage();
    }

    @Test
    @DisplayName("최신 종가 업데이트")
    public void testTrackClosePriceAdd() throws Exception {
        // Given
        ClosePriceResponse closePriceResponse = mock(ClosePriceResponse.class);
        when(trackService.selectBinanceClosePrice()).thenReturn(closePriceResponse);

        // When
        scheduleService.trackClosePriceAdd();

        // Then
        verify(trackService).insertClosePrice(closePriceResponse);
    }
}