package org.jdatepicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class LocalDateModel extends AbstractDateModel<LocalDate> {

    public LocalDateModel(LocalDate initialValue) {
        setValue(initialValue);
    }

    @Override
    protected LocalDate fromCalendar(Calendar from) {
        TimeZone zone = from.getTimeZone();
        ZoneId zoneId = null == zone ? ZoneId.systemDefault() : zone.toZoneId();
        return LocalDateTime.ofInstant(from.toInstant(), zoneId).toLocalDate();
    }

    @SuppressWarnings("MagicConstant")
    @Override
    protected Calendar toCalendar(LocalDate from) {
        Calendar c = Calendar.getInstance();
        c.set(from.getYear(),from.getMonthValue() - 1, from.getDayOfMonth());
        return c;
    }
}
