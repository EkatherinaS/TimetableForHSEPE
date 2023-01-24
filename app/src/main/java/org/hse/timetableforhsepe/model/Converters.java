package org.hse.timetableforhsepe.model;

import android.util.Log;

import androidx.room.TypeConverter;

import org.apache.commons.lang3.text.WordUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    public static Date dateToFullFormat(String date) throws ParseException {
        SimpleDateFormat fullDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return fullDateFormat.parse(date);
    }

    public static String dateToSimpleFormat(Date date) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("HH:mm, EEEE", Locale.forLanguageTag("ru"));
        return simpleDateFormat.format(date);
    }

    public static String dateToWeekdayDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEEE, d MMMM", Locale.forLanguageTag("ru"));
        return WordUtils.capitalize(simpleDateFormat.format(date));
    }

    public static String dateToWeekdayFormat(Date date) {
        SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("EEEE", Locale.forLanguageTag("ru"));
        return simpleDateFormat.format(date);
    }

    public static String dateToTimeFormat(Date date) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("HH:mm", Locale.forLanguageTag("ru"));
        return simpleDateFormat.format(date);
    }
}
