package com.bolotin.trata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeConverter {

    private static final SimpleDateFormat EXPORT_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd'T'HH-mm", Locale.getDefault());

    private static final SimpleDateFormat DB_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    private static final SimpleDateFormat FULL_MONTH_FORMATTER =
            new SimpleDateFormat("LLLL yyyy", Locale.getDefault());

    private static final SimpleDateFormat YEAR_MONTH_FORMATTER =
            new SimpleDateFormat("yyyy-MM", Locale.getDefault());

    private static final SimpleDateFormat DATE_PICKER_FORMATTER =
            new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private static final SimpleDateFormat TIME_PICKER_FORMATTER =
            new SimpleDateFormat("HH:mm", Locale.getDefault());

    private static final SimpleDateFormat ANALYTICS_FORMATTER =
            new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    private static final SimpleDateFormat DAY_OF_MONTH_FORMATTER =
            new SimpleDateFormat("dd MMM", Locale.getDefault());

    private static final SimpleDateFormat CATEGORY_ANALYTICS_FORMATTER =
            new SimpleDateFormat("yyyyMM", Locale.getDefault());

    public static String formatExport(Date date) {
        return EXPORT_FORMATTER.format(date);
    }

    public static String formatDb(Date date) {
        return DB_FORMATTER.format(date);
    }

    public static String formatFullMonth(Date date) {
        return FULL_MONTH_FORMATTER.format(date);
    }

    public static String formatYearMonth(Date date) {
        return YEAR_MONTH_FORMATTER.format(date);
    }

    public static String formatDatePicker(Date date) {
        return DATE_PICKER_FORMATTER.format(date);
    }

    public static String formatTimePicker(Date date) {
        return TIME_PICKER_FORMATTER.format(date);
    }

    public static String formatAnalytics(Date date) {
        return ANALYTICS_FORMATTER.format(date);
    }

    public static String formatDayOfMonth(Date date) {
        return DAY_OF_MONTH_FORMATTER.format(date);
    }

    public static Date parseDb(String date) throws ParseException {
        return DB_FORMATTER.parse(date);
    }

    public static Date parseFullMonth(String date) throws ParseException {
        return FULL_MONTH_FORMATTER.parse(date);
    }

    public static Date parseYearMonth(String date) throws ParseException {
        return YEAR_MONTH_FORMATTER.parse(date);
    }

    public static Date parseAnalytics(String date) throws ParseException {
        return ANALYTICS_FORMATTER.parse(date);
    }

    public static Date parseCategoryAnalytics(String date) throws ParseException {
        return CATEGORY_ANALYTICS_FORMATTER.parse(date);
    }
}