package com.example.message.application;

import com.example.global.config.TelegramConfig;
import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;
import com.example.message.dao.CoinRepository;
import com.example.message.domain.Coin;
import com.example.message.dto.CoinStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.global.util.MessageConstants.FIFTY_AVERAGE_BREAKDOWN;


@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private static final String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";
    private static final int TELEGRAM_SUCCESS_CODE = 200;
    private static final String SYMBOL_BTC_USDT = "BTCUSDT";
    private static final String HTTP_GET_METHOD = "GET";
    @PersistenceContext
    private final EntityManager entityManager;
    private final CoinRepository coinRepository;
    private final TelegramConfig telegramConfig;

    public MessageServiceImpl(EntityManager entityManager, CoinRepository coinRepository, TelegramConfig telegramConfig) {
        this.entityManager = entityManager;
        this.telegramConfig = telegramConfig;
        this.coinRepository = coinRepository;
    }
    @Override
    @Transactional
    public void priceBreakout() throws Exception {
        //String message = FIFTY_AVERAGE_BREAKOUT;
        String message = "부하테스트";
        String urlString = String.format("%s%s/sendMessage?chat_id=%s&text=%s",
                TELEGRAM_BASE_URL, telegramConfig.getToken(), telegramConfig.getChat(), URLEncoder.encode(message, "UTF-8"));

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HTTP_GET_METHOD);

        if (conn.getResponseCode() != TELEGRAM_SUCCESS_CODE) {
            throw new WhaleException(WhaleExceptionType.MESSAGE_ERROR_SEND);
        }else{
            this.updateCoinStatusToUp(SYMBOL_BTC_USDT);
        }

        conn.disconnect();
    }

    public void updateCoinStatusToUp(String symbol) {
        String jpql = "UPDATE Coin c SET c.status = 'UP' WHERE c.symbol = :symbol";
        entityManager.createQuery(jpql)
                .setParameter("symbol", symbol)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void priceBreakdown() throws Exception {
        String message = FIFTY_AVERAGE_BREAKDOWN;
        String urlString = String.format("%s%s/sendMessage?chat_id=%s&text=%s",
                TELEGRAM_BASE_URL, telegramConfig.getToken(), telegramConfig.getChat(), URLEncoder.encode(message, "UTF-8"));

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HTTP_GET_METHOD);

        if (conn.getResponseCode() != TELEGRAM_SUCCESS_CODE) {
            throw new WhaleException(WhaleExceptionType.MESSAGE_ERROR_SEND);
        }else{
            this.updateCoinStatusToDown(SYMBOL_BTC_USDT);
        }

        conn.disconnect();
    }

    public void updateCoinStatusToDown(String symbol) {
        String jpql = "UPDATE Coin c SET c.status = 'DOWN' WHERE c.symbol = :symbol";
        entityManager.createQuery(jpql)
            .setParameter("symbol", symbol)
            .executeUpdate();
    }

    @Override
    public void likeBTC() {
        Coin btcCoin = coinRepository.findBySymbol(SYMBOL_BTC_USDT);
        btcCoin.setLikeCount(btcCoin.getLikeCount() + 1);
        coinRepository.save(btcCoin);
        log.info(SYMBOL_BTC_USDT + "가 좋아요");
    }

    @Override
    public CoinStatusResponse selectBTCStatus() throws Exception {
        String btcStatus = coinRepository.findStatusBySymbol(SYMBOL_BTC_USDT);
        return new CoinStatusResponse(btcStatus);
    }
}
