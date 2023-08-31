package com.example.message.service;

import com.example.message.application.MessageService;
import com.example.message.dao.CoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
@DisplayName("메세지 서비스 테스트")
public class MessageServiceTest {
    @Mock
    private CoinRepository coinRepository;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("텔레그램 돌파 알림 테스트")
    @Test
    public void telegramMessageTest() throws Exception {
        messageService.priceBreakout();

        verify(messageService, times(1)).priceBreakout();
    }
}