package com.example.global.common;

/**
 * packageName    : com.example.global.common
 * fileName       : SignalType
 * author         : 정재윤
 * date           : 2023-08-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-22        정재윤       최초 생성
 */
public enum SignalType {
    CURRENT_HIGHER_THAN_LAST("UP"),
    CURRENT_LOWER_THAN_LAST("DOWN");

    private final String value;

    SignalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
