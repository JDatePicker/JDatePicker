package org.jdatepicker;

import java.time.LocalDateTime;

public class LocalDateTimeModel extends AbstractLocalDateTimeModel<LocalDateTime> {

    public LocalDateTimeModel(LocalDateTime initialValue) {
        super(initialValue);
    }

    @Override
    protected LocalDateTime toLocalDateTime(LocalDateTime from) {
        return from;
    }

    @Override
    protected LocalDateTime fromLocalDateTime(LocalDateTime from) {
        return from;
    }
}
