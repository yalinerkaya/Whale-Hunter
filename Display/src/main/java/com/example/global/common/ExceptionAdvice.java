/**
 * Copyright cashierest Corp. All rights reserved.
 * <p>
 * cashierest Translator
 */
package com.example.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Result> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //log.error("handleMethodArgumentNotValidException", e);
        final Result response = new Result();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Result> handleBindException(BindException e) {
        //log.error("handleBindException", e);
        final Result response = new Result();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Result> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        //log.error("handleMethodArgumentTypeMismatchException", e);
        final Result response = new Result();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Result> handleBusinessException(final Exception e) {
        //log.error("handleEntityNotFoundException", e);
        final Result response = new Result();
        return new ResponseEntity<>(response, HttpStatus.valueOf(500));
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Result> handleException(Exception e) {
        //log.error("handleEntityNotFoundException", e);
        final Result response = new Result();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}