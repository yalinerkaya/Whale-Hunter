package com.example.track;

import com.example.global.exception.WhaleException;
import com.example.track.application.TrackService;
import com.example.track.application.TrackServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.domain.ClosePrice;
import com.example.track.domain.MoveAverage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.track
 * fileName       : TrackMoveAverageTest
 * author         : 정재윤
 * date           : 2023-07-24
 * description    : 종가를 조회, 이동평균 계산중 발생 가능한 경우의 수 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@DisplayName("이동평균 테스트")
@ExtendWith(MockitoExtension.class)
class TrackMoveAverageTest {

    @Mock
    private MoveAverageRepository moveAverageRepository;

    @InjectMocks
    private TrackService trackService = new TrackServiceImpl(null, moveAverageRepository);
    private static final int FIFTY = 50;

    private List<ClosePrice> createClosePricesList(int size) {
        List<ClosePrice> closePrices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            closePrices.add(new ClosePrice("TEST", BigDecimal.valueOf(10000 + i), LocalDateTime.now().minusDays(i)));
        }
        return closePrices;
    }

    @Nested
    @DisplayName("insertMoveAverage 메소드 테스트")
    class InsertMoveAverageTest {

        @Test
        @DisplayName("종가 리스트가 null인 경우 WhaleException 발생")
        void testInsertMoveAverageWithNullClosePrices() {
            // Given
            List<ClosePrice> closePrices = null;

            // When, Then
            assertThrows(WhaleException.class, () -> trackService.insertMoveAverage(closePrices));
        }

        @Test
        @DisplayName("종가 리스트가 비어있는 경우 WhaleException 발생")
        void testInsertMoveAverageWithEmptyClosePrices() {
            // Given
            List<ClosePrice> closePrices = new ArrayList<>();

            // When, Then
            assertThrows(WhaleException.class, () -> trackService.insertMoveAverage(closePrices));
        }

        @Test
        @DisplayName("종가 리스트의 크기가 50 미만인 경우 WhaleException 발생")
        void testInsertMoveAverageWithLessThanFiftyClosePrices() {
            // Given
            List<ClosePrice> closePrices = createClosePricesList(FIFTY - 1);

            // When, Then
            assertThrows(WhaleException.class, () -> trackService.insertMoveAverage(closePrices));
        }

        @Test
        @DisplayName("종가 리스트의 크기가 50인 경우 정상적으로 MoveAverage 저장")
        void testInsertMoveAverageWithFiftyClosePrices() throws Exception {
            // Given
            List<ClosePrice> closePrices = createClosePricesList(FIFTY);

            // When
            trackService.insertMoveAverage(closePrices);

            // Then
            verify(moveAverageRepository, times(1)).save(any(MoveAverage.class));
        }
    }
}
