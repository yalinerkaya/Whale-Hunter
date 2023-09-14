package com.example.track;

import com.example.track.api.TrackApi;
import com.example.track.application.TrackSignalService;
import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(TrackApi.class)
public class TrackApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClosePriceRepository closePriceRepository;

    @MockBean
    private MoveAverageRepository moveAverageRepository;

    @MockBean
    private TrackSignalServiceImpl trackSignalServiceImpl;

    @Test
    @DisplayName("BTC 50일 종가 리스트 저장 Test")
    public void BTC_50_DAYS_CLOSE_PRICE_LIST_SAVE() throws Exception{
        this.mockMvc
            .perform(post("/track/close"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("BTC 50일 이동평균선 저장 Test")
    public void BTC_50_DAYS_MOVE_AVERAGE_PRICE_SAVE() throws Exception{
        this.mockMvc
            .perform(post("/track/average"))
            .andExpect(status().isOk())
            .andDo(print());
    }
}