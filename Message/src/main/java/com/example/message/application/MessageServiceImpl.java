package com.example.message.application;

import com.example.global.config.TelegramConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.message.domain.Telegram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.global.util.MessageConstants.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final TelegramConfig telegramConfig;

    @Override
    @Transactional
    public void priceBreakout() throws Exception {
        sendMessageAndUpdateStatus(FIFTY_AVERAGE_BREAKOUT, SYMBOL_BTC_USDT, UP);
    }

    @Override
    @Transactional
    public void priceBreakdown() throws Exception {
        sendMessageAndUpdateStatus(FIFTY_AVERAGE_BREAKDOWN, SYMBOL_BTC_USDT, DOWN);
    }

    public void sendMessageAndUpdateStatus(String message, String symbol, String status) throws Exception {
        URL url = Telegram.buildSendMessageUrl(message, telegramConfig);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HTTP_GET_METHOD);

        if (conn.getResponseCode() != TELEGRAM_SUCCESS_CODE) {
            throw new WhaleException(WhaleExceptionType.MESSAGE_ERROR_SEND);
        } else {
            updateCoinStatus(symbol, status);
        }

        conn.disconnect();
    }

    public void updateCoinStatus(String symbol, String status) throws Exception {
        String jpql = "UPDATE Coin c SET c.status = :status WHERE c.symbol = :symbol";
        entityManager.createQuery(jpql)
            .setParameter(SYMBOL, symbol)
            .setParameter(STATUS, status)
            .executeUpdate();
    }
}
