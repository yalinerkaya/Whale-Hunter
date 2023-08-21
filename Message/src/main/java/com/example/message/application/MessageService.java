package com.example.message.application;

import com.example.message.dto.CoinStatusResponse;

public interface MessageService {
    void priceBreakout() throws Exception;

    void priceBreakdown() throws Exception;

    CoinStatusResponse selectBTCStatus() throws Exception;
}
