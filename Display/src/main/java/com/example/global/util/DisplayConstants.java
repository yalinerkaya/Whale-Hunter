package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

public class DisplayConstants {
    private DisplayConstants() {
        throw new WhaleException(WhaleExceptionType.INVALID_BINDING);
    }
    public static final String SUCCESS = "0";
    public static final String BLANK = "";

    public static final String DATA_API_URL = "/data";

    public static final String DATA_ATTRIBUTE_NAME = "data_api_url";
    public static final String VIEW_DISPLAY = "display";

    public static final int ONE_MINUTE = 60;
}
