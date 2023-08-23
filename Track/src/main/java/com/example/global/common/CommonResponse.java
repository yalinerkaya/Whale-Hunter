package com.example.global.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import static com.example.global.util.TrackConstants.SUCCESS;

/**
 * packageName    : com.example.global.common
 * fileName       : CommonResponse
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
public class CommonResponse<T> extends ResponseEntity<Result<T>> {

    public CommonResponse() {
        super(new Result<T>(SUCCESS, null, null), HttpStatus.OK);
    }

    public CommonResponse(T body) {
        super(new Result<T>(SUCCESS, null, body), HttpStatus.OK);
    }

    public CommonResponse(HttpStatus status, @Nullable T body) {
        super(new Result<>(String.valueOf(status), null, body), status);
    }

    public CommonResponse(String message) {
        super(new Result<>(null, message, null), HttpStatus.OK);
    }

    public CommonResponse(String message, @Nullable T body) {
        super(new Result<>(SUCCESS, message, body), HttpStatus.OK);
    }

    public CommonResponse(HttpStatus status, String message, @Nullable T body) {
        super(new Result<>(String.valueOf(status), message, body), status);
    }
}
