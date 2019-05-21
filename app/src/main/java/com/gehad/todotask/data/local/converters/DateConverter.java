package com.gehad.todotask.data.local.converters;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import java.util.Date;


public class DateConverter {
    private DateConverter() {
        throw new AssertionError();
    }

    @TypeConverter
    public static Date fromLong(@Nullable Long dateLong) {
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(@Nullable Date date) {
        return date == null ? null : date.getTime();
    }
}
