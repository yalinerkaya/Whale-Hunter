package com.example.global.util;

import com.example.global.exception.WhaleException;
import com.example.global.exception.WhaleExceptionType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String convertTimestampToTimeString(String timestamp) {
        ZonedDateTime zdt = ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime localDateTime = LocalDateTime.of(zdt.getYear(), zdt.getMonth(), zdt.getDayOfMonth(), zdt.getHour(), zdt.getMinute());
        return localDateTime.toString();
    }
}
