package com.example.global.exception;

import org.springframework.http.HttpStatus;

/**
 * packageName    : com.example.global.exception
 * fileName       : WhaleException
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
public class WhaleException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String message;

    public WhaleException() {
        super();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = WhaleExceptionType.INTERNAL_SERVER_ERROR.getCode();
        this.message = WhaleExceptionType.INTERNAL_SERVER_ERROR.getMessage();
    }

    public WhaleException(WhaleExceptionType whaleExceptionType) {
        super();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = whaleExceptionType.getCode();
        this.message = whaleExceptionType.getMessage();
    }
}
