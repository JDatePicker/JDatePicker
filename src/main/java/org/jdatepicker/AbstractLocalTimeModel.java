package org.jdatepicker;

import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  Created 12 November 2020
 *  Updated 12 November 2020
 *
 * @param <T> The type of this model (e.g. java.time.LocalTime, java.util.Calendar)
 * @author Marc Jakobi
 */
public abstract class AbstractLocalTimeModel<T> implements TimeModel<T> {

    public static final String PROPERTY_HOUR = "hour";
    public static final String PROPERTY_MINUTE = "minute";
    public static final String PROPERTY_SECOND = "second";
    public static final String PROPERTY_NANOSECOND = "nanosecond";
    public static final String PROPERTY_VALUE = "value";
    public static final String PROPERTY_SELECTED = "selected";

    private boolean selected;
    private LocalTime timeValue;

    private final SwingPropertyChangeSupport propertyChangeSupport = new SwingPropertyChangeSupport(this);

    public AbstractLocalTimeModel(T initialValue) {
        this.timeValue = toLocalTime(initialValue);
    }

    @Override
    public void setValueFromString(String value) {
        setValue(fromLocalTime(LocalTime.parse(value)));
    }

    @Override
    public int getHour() {
        return this.timeValue.getHour();
    }

    @Override
    public TimeModel<T> setHour(int hour) {
        int oldHour = getHour();
        T oldValue = getValue();
        this.timeValue = this.timeValue.withHour(hour);
        fireChangeEvent();
        firePropertyChange(PROPERTY_HOUR, oldHour, getHour());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public int getMinute() {
        return this.timeValue.getMinute();
    }

    @Override
    public TimeModel<T> setMinute(int minute) {
        int oldMinute = getMinute();
        T oldValue = getValue();
        this.timeValue = this.timeValue.withMinute(minute);
        fireChangeEvent();
        firePropertyChange(PROPERTY_MINUTE, oldMinute, getMinute());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public int getSecond() {
        return this.timeValue.getSecond();
    }

    @Override
    public TimeModel<T> setSecond(int second) {
        int oldSecond = getSecond();
        T oldValue = getValue();
        this.timeValue = this.timeValue.withSecond(second);
        fireChangeEvent();
        firePropertyChange(PROPERTY_SECOND, oldSecond, getSecond());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public int getNanosecond() {
        return this.timeValue.getNano();
    }

    @Override
    public TimeModel<T> setNanoSecond(int nanoSecond) {
        int oldNanoSecond = getNanosecond();
        T oldValue = getValue();
        this.timeValue = this.timeValue.withNano(nanoSecond);
        fireChangeEvent();
        firePropertyChange(PROPERTY_NANOSECOND, oldNanoSecond, getNanosecond());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public TimeModel<T> addHours(int numberOfHours) {
        if (numberOfHours == 0) {
            return this;
        }
        int newHour = this.timeValue.plusHours(numberOfHours).getHour();
        return setHour(newHour);
    }

    @Override
    public TimeModel<T> addMinutes(int numberOfMinutes) {
        if (numberOfMinutes == 0) {
            return this;
        }
        int oldHour = this.timeValue.getHour();
        int oldMinute = this.timeValue.getMinute();
        T oldValue = getValue();
        this.timeValue = this.timeValue.plusMinutes(numberOfMinutes);
        fireChangeEvent();
        firePropertyChange(PROPERTY_HOUR, oldHour, getHour());
        firePropertyChange(PROPERTY_MINUTE, oldMinute, getMinute());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public TimeModel<T> addSeconds(int numberOfSeconds) {
        if (numberOfSeconds == 0) {
            return this;
        }
        int oldHour = this.timeValue.getHour();
        int oldMinute = this.timeValue.getMinute();
        int oldSecond = this.timeValue.getSecond();
        T oldValue = getValue();
        this.timeValue = this.timeValue.plusSeconds(numberOfSeconds);
        fireChangeEvent();
        firePropertyChange(PROPERTY_HOUR, oldHour, getHour());
        firePropertyChange(PROPERTY_MINUTE, oldMinute, getMinute());
        firePropertyChange(PROPERTY_SECOND, oldSecond, getSecond());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public TimeModel<T> addNanoSeconds(int numberOfNanoseconds) {
        if (numberOfNanoseconds == 0) {
            return this;
        }
        int oldHour = this.timeValue.getHour();
        int oldMinute = this.timeValue.getMinute();
        int oldSecond = this.timeValue.getSecond();
        int oldNanoSecond = this.timeValue.getNano();
        T oldValue = getValue();
        this.timeValue = this.timeValue.plusNanos(numberOfNanoseconds);
        fireChangeEvent();
        firePropertyChange(PROPERTY_HOUR, oldHour, getHour());
        firePropertyChange(PROPERTY_MINUTE, oldMinute, getMinute());
        firePropertyChange(PROPERTY_SECOND, oldSecond, getSecond());
        firePropertyChange(PROPERTY_NANOSECOND, oldNanoSecond, getNanosecond());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        return this;
    }

    @Override
    public T getValuePlusHours(int numberOfHours) {
        return fromLocalTime(timeValue.plusHours(numberOfHours));
    }

    @Override
    public T getValuePlusMinutes(int numberOfMinutes) {
        return fromLocalTime(timeValue.plusMinutes(numberOfMinutes));
    }

    @Override
    public T getValuePlusSeconds(int numberOfSeconds) {
        return fromLocalTime(timeValue.plusSeconds(numberOfSeconds));
    }

    @Override
    public T getValuePlusNanoseconds(int numberOfNanoseconds) {
        return fromLocalTime(timeValue.plusNanos(numberOfNanoseconds));
    }

    @Override
    public Vector<T> getFullHours() {
        return IntStream.range(0, 24)
                .mapToObj(hour -> LocalTime.of(hour, 0, 0))
                .map(this::fromLocalTime)
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public T getValue() {
        return fromLocalTime(this.timeValue);
    }

    @Override
    public void setValue(T value) {
        int oldHour = getHour();
        int oldMinute = getMinute();
        int oldSecond = getSecond();
        int oldNanoSecond = getNanosecond();
        T oldValue = getValue();
        boolean oldSelected = isSelected();
        if (null == value) {
            this.selected = false;
        } else {
            this.selected = true;
            this.timeValue = toLocalTime(value);
        }
        fireChangeEvent();
        firePropertyChange(PROPERTY_HOUR, oldHour, getHour());
        firePropertyChange(PROPERTY_MINUTE, oldMinute, getMinute());
        firePropertyChange(PROPERTY_SECOND, oldSecond, getSecond());
        firePropertyChange(PROPERTY_NANOSECOND, oldNanoSecond, getNanosecond());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        firePropertyChange(PROPERTY_SELECTED, oldSelected, isSelected());
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean selected) {
        boolean oldSelected = isSelected();
        this.selected = selected;
        fireChangeEvent();
        firePropertyChange(PROPERTY_SELECTED, oldSelected, isSelected());
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        propertyChangeSupport.addChangeListener(changeListener);
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
        propertyChangeSupport.addChangeListener(changeListener);
    }

    protected void fireChangeEvent() {
        propertyChangeSupport.fireChangeEvent();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected abstract LocalTime toLocalTime(T from);

    protected abstract T fromLocalTime(LocalTime from);
}
