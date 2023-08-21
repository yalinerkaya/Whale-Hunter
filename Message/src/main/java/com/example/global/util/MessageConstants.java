package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

public class MessageConstants {
    private MessageConstants() {
        throw new WhaleException(WhaleExceptionType.INVALID_BINDING);
    }
    public static final int TELEGRAM_SUCCESS_CODE = 200;
    public static final String SYMBOL_BTC_USDT = "BTCUSDT";
    public static final String HTTP_GET_METHOD = "GET";
    public static final String SUCCESS = "0";
    public static final String BLANK = "";

    public static final String UP = "UP";

    public static final String SYMBOL = "symbol";

    public static final String STATUS = "status";

    public static final String DOWN = "DOWN";
    public static final String COMMAND_BTC = "/btc";
    public static final String MESSAGE_TEXT_BTC = "BTC 떡상 기원";
    public static final String LOCALHOST_ENDPOINT = "http://localhost:8003/btc";
    public static final String FIFTY_AVERAGE_BREAKOUT = "비트코인 매수 시그널이 포착되었습니다.";
    public static final String FIFTY_AVERAGE_BREAKDOWN = "비트코인 매도 시그널이 포착되었습니다.";
}
