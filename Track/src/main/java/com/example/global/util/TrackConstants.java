package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

public class TrackConstants {
    private TrackConstants() {
        throw new WhaleException(WhaleExceptionType.INVALID_BINDING);
    }
    public static final String BTC_USDT = "BTCUSDT";

    public static final String SUCCESS = "0";

    public static final String BLANK = "";

    public static final String UTC = "UTC";

    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int FIFTY = 50;

    public static final int FIVE_HUNDRED_LIMIT= 500;

    public static final int TWO_DECIMAL_PLACES = 2;

    public static final String SIDE_BUY = "buy";
    public static final String SIDE_SELL = "sell";
    public static final String STATE_DIR = "/tmp/kafka-streams";

    public static final String EXCHANGE_ORIGIN = "https://exchange.blockchain.com";
    public static final String WEBSOCKET_URI = "wss://ws.blockchain.info/mercury-gateway/v1/ws";
    public static final String CHANNEL_SUBSCRIPTION = "채널 구독 BTC-USD";

    public static final String ACTION = "subscribe";
    public static final String CHANNEL = "trades";
    public static final String SYMBOL = "BTC-USD";
    public static final int CONNECTION_TIMEOUT_MS = 10000;

    public static final String MIDNIGHT = "0 0 0 * * ?";
    public static final String MIDNIGHT_10_SECONDS = "10 0 0 * * ?";

    public static final String TRADE_MESSAGE_TOPIC = "trade_test";
}
