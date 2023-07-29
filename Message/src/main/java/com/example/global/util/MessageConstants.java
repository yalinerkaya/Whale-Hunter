package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

public class MessageConstants {
    public static final String BTC_USDT = "BTCUSDT";
    public static final String SUCCESS = "0";
    public static final String BLANK = "";
    public static final String UTC = "UTC";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int FIFTY = 50;
    public static final int FIVE_HUNDRED_LIMIT = 500;
    public static final int TWO_DECIMAL_PLACES = 2;

    public static final String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";

    public static final String FIFTY_AVERAGE_BREAKOUT = "비트코인 매수 시그널이 포착되었습니다.";

    private MessageConstants() {
        throw new WhaleException(WhaleExceptionType.INVALID_BINDING);
    }
}
