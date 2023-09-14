package com.example.track;

import com.example.track.api.TrackApi;
import com.example.track.application.TrackService;
import com.example.track.application.TrackServiceImpl;
import com.example.track.application.TrackSignalService;
import com.example.track.application.TrackSignalServiceImpl;
import com.example.track.dao.ClosePriceRepository;
import com.example.track.dao.MoveAverageRepository;
import com.example.track.dto.ClosePriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TrackApi.class)
public class TrackApiTest {
    @Autowired
    private MockMvc mockMvc;

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