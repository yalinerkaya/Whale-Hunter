package com.example.track.api;

import com.example.track.AbstractRestDocsTests;
import com.example.track.application.TrackServiceImpl;
import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TrackApi.class)
public class TrackApiTest extends AbstractRestDocsTests {

    @MockBean
    private TrackServiceImpl trackService;

    @MockBean
    private TrackSignalServiceImpl trackSignalService;

    @MockBean
    private ClosePriceRepository closePriceRepository;

    @MockBean
    private MoveAverageRepository moveAverageRepository;

    @Test
    @DisplayName("BTC 50일 종가 리스트 저장 Test")
    public void BTC_50_DAYS_CLOSE_PRICE_LIST_SAVE() throws Exception{
        this.mockMvc
            .perform(post("/track/close"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
    }

    @Test
    @DisplayName("BTC 50일 이동평균선 저장 Test")
    public void BTC_50_DAYS_MOVE_AVERAGE_PRICE_SAVE() throws Exception{
        this.mockMvc
            .perform(post("/track/average"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
    }

    @Test
    @DisplayName("BTC 현재 종가와 이동평균선 비교후 상태 저장 Test")
    public void BTC_STATUS_SAVE() throws Exception{
        this.mockMvc
            .perform(post("/track/status"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
    }

    @Test
    @DisplayName("BTC 새로운 종가 저장 Test")
    public void BTC_NEW_CLOSE_PRICE_SAVE() throws Exception{
        this.mockMvc
            .perform(post("/track/new-close"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
    }
}