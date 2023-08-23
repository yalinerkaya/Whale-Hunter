package com.example.global.common;


import lombok.*;

import static com.example.global.util.MessageConstants.BLANK;
import static com.example.global.util.MessageConstants.SUCCESS;

/**
 * packageName    : com.example.global.common
 * fileName       : Result
 * author         : Jay
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-23        Jay       최초 생성
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Result<T> {

    private T data;

    private String code = SUCCESS;

    private String message = BLANK;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
