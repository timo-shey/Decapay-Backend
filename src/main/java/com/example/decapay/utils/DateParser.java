package com.example.decapay.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static LocalDate parseDate(String dateString) {
        Matcher matcher = DATE_PATTERN.matcher(dateString);
        if (matcher.matches()) {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } else {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }
}
