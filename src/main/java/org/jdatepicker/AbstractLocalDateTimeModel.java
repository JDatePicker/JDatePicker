package org.jdatepicker;

import org.jdatepicker.factory.ModelFactory;

import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Vector;

public abstract class AbstractLocalDateTimeModel<T> implements DateTimeModel<T> {

    private final DateModel<LocalDate> dateModel;
    private final TimeModel<LocalTime> timeModel;

    public AbstractLocalDateTimeModel(T initialValue) {
        LocalDateTime localDateTime = toLocalDateTime(initialValue);
        this.dateModel = ModelFactory.createLocalDateModel(localDateTime.toLocalDate());
        this.timeModel = ModelFactory.createLocalTimeModel(localDateTime.toLocalTime());
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
    public void setValueFromString(String value) {
        setValue(fromLocalDateTime(LocalDateTime.parse(value)));
    }

    @Override
    public int getHour() {
        return timeModel.getHour();
    }

    @Override
    public DateModel<?> getDateModel() {
        return dateModel;
    }

    @Override
    public TimeModel<?> getTimeModel() {
        return timeModel;
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
    public int getNanosecond() {
        return timeModel.getNanosecond();
    }

    @Override
    public DateTimeModel<T> setNanoSecond(int nanoSecond) {
        timeModel.setNanoSecond(nanoSecond);
        return this;
    }

    @Override
    public DateTimeModel<T> addHours(int numberOfHours) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return this;
        }
        return toModels(localDateTime.plusHours(numberOfHours));
    }

    @Override
    public DateTimeModel<T> addMinutes(int numberOfMinutes) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return this;
        }
        return toModels(localDateTime.plusMinutes(numberOfMinutes));
    }


    @Override
    public DateTimeModel<T> addSeconds(int numberOfSeconds) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return this;
        }
        return toModels(localDateTime.plusSeconds(numberOfSeconds));
    }

    @Override
    public DateTimeModel<T> addNanoSeconds(int numberOfNanoseconds) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return this;
        }
        return toModels(localDateTime.plusNanos(numberOfNanoseconds));
    }

    @Override
    public T getValuePlusHours(int numberOfHours) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return null;
        }
        return fromLocalDateTime(localDateTime.plusHours(numberOfHours));
    }

    @Override
    public T getValuePlusMinutes(int numberOfMinutes) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return null;
        }
        return fromLocalDateTime(localDateTime.plusMinutes(numberOfMinutes));
    }

    @Override
    public T getValuePlusSeconds(int numberOfSeconds) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return null;
        }
        return fromLocalDateTime(localDateTime.plusSeconds(numberOfSeconds));
    }

    @Override
    public T getValuePlusNanoseconds(int numberOfNanoseconds) {
        LocalDateTime localDateTime = fromModels();
        if (localDateTime == null) {
            return null;
        }
        return fromLocalDateTime(localDateTime.plusNanos(numberOfNanoseconds));
    }

    @Override
    public Vector<?> getFullHours() {
        return this.timeModel.getFullHours();
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

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.dateModel.addPropertyChangeListener(listener);
        this.timeModel.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.dateModel.removePropertyChangeListener(listener);
        this.timeModel.removePropertyChangeListener(listener);
    }

    protected abstract LocalDateTime toLocalDateTime(T from);

    protected abstract T fromLocalDateTime(LocalDateTime from);

    private AbstractLocalDateTimeModel<T> toModels(LocalDateTime localDateTime) {
        dateModel.setValue(localDateTime.toLocalDate());
        timeModel.setValue(localDateTime.toLocalTime());
        return this;
    }

    private LocalDateTime fromModels() {
        if (dateModel.getValue() == null || timeModel.getValue() == null) {
            return null;
        }
        return LocalDateTime.of(dateModel.getValue(), timeModel.getValue());
    }

}
