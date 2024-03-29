package com.example.global.exception;

import lombok.Getter;

/**
 * packageName    : com.example.global.exception
 * fileName       : WhaleExceptionType
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Getter
public enum WhaleExceptionType {
    /** SYSTEM EXCEPTION **/
    INTERNAL_SERVER_ERROR("01001", "exception.common.server.error"),
    INVALID_BINDING("01002", "exception.binding.invalid"),
    INVALID_DESERIALIZE("01003", "exception.invalid.deserialize"),
    INVALID_SERIALIZE("01004", "exception.invalid.serialize"),

    /** TRACK **/
    TRACK_REQUIRED_CLOSE_PRICE("01010", "track.required.closeprice"),
    TRACK_INVALID_CLOSE_PRICE("01011", "track.invalid.closePrice"),
    TRACK_INVALID_QUANTITY("01012", "track.invalid.quantity"),
    TRACK_INVALID_PRICE("01013", "track.invalid.price"),
    TRACK_ERROR_WEBSOCKET_CONNECT("01014", "track.error.websocket.connect"),
    TRACK_ERROR_SIGNAL_CHECK("01015", "track.error.signal.check"),

    TRACK_REQUIRED_CANDLESTICK("01016", "track.required.candlestick"),

    TRACK_REQUIRED_MOVE_AVERAGE("01017", "track.required.moveaverage");

    private final String code;
    private final String message;

    WhaleExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
