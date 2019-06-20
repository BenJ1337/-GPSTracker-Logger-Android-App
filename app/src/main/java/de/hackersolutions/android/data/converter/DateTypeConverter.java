package de.hackersolutions.android.data.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {
    @TypeConverter
    public long convertDateToLong(Date timestamp) {
        return timestamp.getTime();

    }

    @TypeConverter
    public Date convertLongToDate(long timestamp) {
        return new Date(timestamp);
    }
}
