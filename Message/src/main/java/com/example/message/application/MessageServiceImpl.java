package com.example.message.application;

import com.example.global.common.StatusCode;
import com.example.global.config.TelegramConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.message.dao.CoinRepository;
import com.example.message.domain.Coin;
import com.example.message.domain.Telegram;
import com.example.message.dto.MessageEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.global.util.MessageConstants.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {


    private final CoinRepository coinRepository;
    private final TelegramConfig telegramConfig;

    @Override
    public boolean selectCompletedEvent(MessageEventRequest messageEventRequest) throws Exception {
        Coin coin = coinRepository.findByTradeUid(messageEventRequest.getTradeUid());

        if(coin == null){
            return false;
        }

        if(coin.getStatus().equals(StatusCode.COMPLETE.getValue())){
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void priceBreakout(MessageEventRequest messageEventRequest) throws Exception {
        sendMessageAndUpdateStatus(FIFTY_AVERAGE_BREAKOUT, messageEventRequest);
    }

    @Override
    @Transactional
    public void priceBreakdown(MessageEventRequest messageEventRequest) throws Exception {
        sendMessageAndUpdateStatus(FIFTY_AVERAGE_BREAKDOWN, messageEventRequest);
    }

    @Override
    @Transactional
    public void insertMessageEvent(MessageEventRequest messageEventRequest) throws Exception {
        coinRepository.save(Coin.createBTCEvent(messageEventRequest));
    }

    public void sendMessageAndUpdateStatus(String message, MessageEventRequest messageEventRequest) throws Exception {
        URL url = Telegram.buildSendMessageUrl(message, telegramConfig);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HTTP_GET_METHOD);

        if (conn.getResponseCode() != TELEGRAM_SUCCESS_CODE) {
            throw new WhaleException(WhaleExceptionType.MESSAGE_ERROR_SEND);
        } else {
            this.updateCoinStatus(messageEventRequest);
        }

        conn.disconnect();
    }

    @Transactional
    public void updateCoinStatus(MessageEventRequest messageEventRequest) {
        Coin coin = coinRepository.findByTradeUid(messageEventRequest.getTradeUid());

        if(ObjectUtils.isEmpty(coin)){
            throw new WhaleException(WhaleExceptionType.MESSAGE_REQUIRED_COIN);
        }

        coinRepository.save(coin.changeCoinStatus(coin, StatusCode.COMPLETE));
    }
}
