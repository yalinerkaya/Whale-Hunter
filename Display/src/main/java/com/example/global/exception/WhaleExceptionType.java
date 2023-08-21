package com.example.global.exception;

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
public enum WhaleExceptionType {
    /** SYSTEM EXCEPTION **/
    INTERNAL_SERVER_ERROR("01001", "exception.common.server.error"),
    INVALID_BINDING("01002", "exception.binding.invalid"),

    /** TRACK **/
    TRACK_REQUIRED_CLOSE_PRICE("01010", "track.required.closePrice"),
    TRACK_INVALID_CLOSE_PRICE("01011", "track.invalid.closePrice"),
    TRACK_INVALID_QUANTITY("01012", "track.invalid.quantity"),
    TRACK_INVALID_PRICE("01013", "track.invalid.price");
    private final String code;
    private final String message;

    WhaleExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
