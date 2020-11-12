package org.jdatepicker;

import javax.swing.event.ChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AbstractDateTimeModel<T> implements DateTimeModel<T> {

    private final DateModel<LocalDate> dateModel;
    private final TimeModel<LocalTime> timeModel;

    public AbstractDateTimeModel(T initialValue) {
        LocalDateTime localDateTime = toLocalDateTime(initialValue);
        this.dateModel = new LocalDateModel(localDateTime.toLocalDate());
        this.timeModel = new LocalTimeModel(localDateTime.toLocalTime());
    }

    @Override
    public int getYear() {
        return dateModel.getYear();
    }

    @Override
    public void setYear(int year) {
        dateModel.setYear(year);
    }

    @Override
    public int getMonth() {
        return dateModel.getMonth();
    }

    @Override
    public void setMonth(int month) {
        dateModel.setMonth(month);
    }

    @Override
    public int getDay() {
        return dateModel.getDay();
    }

    @Override
    public void setDay(int day) {
        dateModel.setDay(day);
    }

    @Override
    public void setDate(int year, int month, int day) {
        dateModel.setDate(year, month, day);
    }

    @Override
    public void addYear(int add) {
        dateModel.addYear(add);
    }

    @Override
    public void addMonth(int add) {
        dateModel.addMonth(add);
    }

    @Override
    public void addDay(int add) {
        dateModel.addDay(add);
    }

    @Override
    public int getHour() {
        return timeModel.getHour();
    }

    @Override
    public DateTimeModel<T> setHour(int hour) {
        timeModel.setHour(hour);
        return this;
    }

    @Override
    public int getMinute() {
        return timeModel.getMinute();
    }

    @Override
    public DateTimeModel<T> setMinute(int minute) {
        timeModel.setMinute(minute);
        return this;
    }

    @Override
    public int getSecond() {
        return timeModel.getSecond();
    }

    @Override
    public DateTimeModel<T> setSecond(int second) {
        timeModel.setSecond(second);
        return this;
    }

    @Override
    public int getNanoSecond() {
        return timeModel.getNanoSecond();
    }

    @Override
    public DateTimeModel<T> setNanoSecond(int nanoSecond) {
        timeModel.setNanoSecond(nanoSecond);
        return this;
    }

    @Override
    public DateTimeModel<T> addHours(int numberOfHours) {
        return toModels(fromModels().plusHours(numberOfHours));
    }

    @Override
    public DateTimeModel<T> addMinutes(int numberOfMinutes) {
        return toModels(fromModels().plusMinutes(numberOfMinutes));
    }


    @Override
    public DateTimeModel<T> addSeconds(int numberOfSeconds) {
        return toModels(fromModels().plusSeconds(numberOfSeconds));
    }

    @Override
    public DateTimeModel<T> addNanoSeconds(int numberOfNanoSeconds) {
        return toModels(fromModels().plusNanos(numberOfNanoSeconds));
    }

    @Override
    public T getValue() {
        return fromLocalDateTime(fromModels());
    }

    @Override
    public void setValue(T value) {
        toModels(toLocalDateTime(value));
    }

    @Override
    public boolean isSelected() {
        return this.dateModel.isSelected() && this.timeModel.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        this.dateModel.setSelected(selected);
        this.timeModel.setSelected(selected);
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        this.dateModel.addChangeListener(changeListener);
        this.timeModel.addChangeListener(changeListener);
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
        this.dateModel.removeChangeListener(changeListener);
        this.timeModel.removeChangeListener(changeListener);
    }
    
    protected abstract LocalDateTime toLocalDateTime(T from);

    protected abstract T fromLocalDateTime(LocalDateTime from);

    private AbstractDateTimeModel<T> toModels(LocalDateTime localDateTime) {
        dateModel.setValue(localDateTime.toLocalDate());
        timeModel.setValue(localDateTime.toLocalTime());
        return this;
    }

    private LocalDateTime fromModels() {
        return LocalDateTime.of(dateModel.getValue(), timeModel.getValue());
    }


    private static class LocalDateModel extends AbstractLocalDateModel<LocalDate> {

        public LocalDateModel(LocalDate initialValue) {
            super(initialValue);
        }

        @Override
        protected LocalDate toLocalDate(LocalDate from) {
            return from;
        }

        @Override
        protected LocalDate fromLocalDate(LocalDate from) {
            return from;
        }
    }

    private static class LocalTimeModel extends AbstractLocalTimeModel<LocalTime> {


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
}
