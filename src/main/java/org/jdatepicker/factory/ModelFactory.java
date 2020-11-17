package org.jdatepicker.factory;

import org.jdatepicker.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class ModelFactory {

    public static DateTimeModel<LocalDateTime> createLocalDateTimeModel(LocalDateTime initial) {
        return new LocalDateTimeModel(initial);
    }

    public static DateModel<LocalDate> createLocalDateModel(LocalDate initial) {
        return new LocalDateModel(initial);
    }

    public static DateModel<java.sql.Date> createSqlDateModel(java.sql.Date initial) {
        return new SqlDateModel(initial);
    }

    public static DateModel<Calendar> createUtilCalendarDateModel(Calendar initial) {
        return new UtilCalendarModel(initial);
    }

    public static DateModel<Date> createUtilDateModel(Date initial) {
        return new UtilDateModel(initial);
    }

    public static TimeModel<LocalTime> createLocalTimeModel() {
        return createLocalTimeModel(LocalTime.now());
    }

    public static TimeModel<LocalTime> createLocalTimeModel(LocalTime initial) {
        return new LocalTimeModel(initial);
    }
}
