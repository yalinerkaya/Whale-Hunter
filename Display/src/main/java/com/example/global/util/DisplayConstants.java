package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

public class DisplayConstants {
    private DisplayConstants() {
        throw new WhaleException(WhaleExceptionType.INVALID_BINDING);
    }
    public static final String SUCCESS = "0";
    public static final String BLANK = "";

    public static final int ONE_MINUTE = 60;
}
