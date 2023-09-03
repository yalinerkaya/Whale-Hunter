package com.example.global.common;

/**
 * packageName    : com.example.global.common
 * fileName       : SignalType
 * author         : Jay
 * date           : 2023-08-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-22        Jay       최초 생성
 */
public enum StatusCode {
    WAIT("WAIT"),
    COMPLETE("COMPLETE");

    private final String value;

    StatusCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
