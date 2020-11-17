package org.jdatepicker;

import java.time.LocalTime;

public class LocalTimeModel extends AbstractLocalTimeModel<LocalTime> {


    public LocalTimeModel(LocalTime initialValue) {
        super(initialValue);
    }

    @Override
    protected LocalTime toLocalTime(LocalTime from) {
        return from;
    }

    @Override
    protected LocalTime fromLocalTime(LocalTime from) {
        return from;
    }
}