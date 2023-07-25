package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    private DateUtils() {
        throw new WhaleException(WhaleExceptionType.INVALID_BINDING);
    }
    public static long getTodayStartTimeInMillis() {
        return Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli();
    }

    public static long getStartTimeBeforeDays(int days) {
        return Instant.now().truncatedTo(ChronoUnit.DAYS).minus(days, ChronoUnit.DAYS).toEpochMilli();
    }

    public static LocalDate getToday() {
        return LocalDate.now();
    }
}
