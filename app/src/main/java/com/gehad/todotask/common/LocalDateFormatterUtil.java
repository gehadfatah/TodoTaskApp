package com.gehad.todotask.common;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public final class LocalDateFormatterUtil {

    private static final String MONTH_SHORT_DAY_AND_YEAR_FORMAT = "MMM dd, yyyy";

    private LocalDateFormatterUtil() {
        throw new AssertionError();
    }

    public static String getShortMonthDayAndYearFormattedDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(MONTH_SHORT_DAY_AND_YEAR_FORMAT));
    }
}
