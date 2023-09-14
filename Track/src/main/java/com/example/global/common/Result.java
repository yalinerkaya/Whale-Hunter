package com.example.global.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static com.example.global.util.TrackConstants.BLANK;
import static com.example.global.util.TrackConstants.SUCCESS;

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

    private String code = SUCCESS;
    private String message = BLANK;
    private T data;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
