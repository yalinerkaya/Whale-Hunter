package com.example.message.application;

import com.example.message.dto.MessageEventRequest;

public interface MessageService {
    void priceBreakout(MessageEventRequest messageEventRequest) throws Exception;

    void priceBreakdown(MessageEventRequest messageEventRequest) throws Exception;

    void insertMessageEvent(MessageEventRequest messageEventRequest) throws Exception;
}
