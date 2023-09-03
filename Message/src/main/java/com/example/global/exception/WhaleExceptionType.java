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
    MESSAGE_ERROR_SEND("03000", "message.error.send"),

    MESSAGE_REQUIRED_COIN("03001", "message.required.coin"),

    MESSAGE_ERROR_CONSUME("03002", "message.error.consume");
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
