/**
 Copyright 2004 Juan Heyns. All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are
 permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this list of
 conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice, this list
 of conditions and the following disclaimer in the documentation and/or other materials
 provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those of the
 authors and should not be interpreted as representing official policies, either expressed
 or implied, of Juan Heyns.
 */
package org.jdatepicker;

import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

/**
 * This date model uses a LocalDate for calculations, instead of a Calendar instance.
 * To be consistent with the {@link AbstractDateModel} implementation, this model transforms the day and month to be zero-based.
 *
 * Created 12 November 2020
 * Updated 12 November 2020
 *
 * @param <T> The type of this model (e.g. java.util.Date, java.util.Calendar)
 * @author Marc Jakobi
 */
public abstract class AbstractLocalDateModel<T> implements DateModel<T> {

    public static final String PROPERTY_YEAR = "year";
    public static final String PROPERTY_MONTH = "month";
    public static final String PROPERTY_DAY = "day";
    public static final String PROPERTY_VALUE = "value";
    public static final String PROPERTY_SELECTED = "selected";

    private boolean selected;
    private LocalDate dateTimeValue;
    private final SwingPropertyChangeSupport propertyChangeSupport = new SwingPropertyChangeSupport(this);

    protected AbstractLocalDateModel(T initialValue) {
        selected = false;
        dateTimeValue = toLocalDate(initialValue);
    }

    public synchronized void addChangeListener(ChangeListener changeListener) {
        propertyChangeSupport.addChangeListener(changeListener);
    }

    public synchronized void removeChangeListener(ChangeListener changeListener) {
        propertyChangeSupport.removeChangeListener(changeListener);
    }

    protected synchronized void fireChangeEvent() {
        propertyChangeSupport.fireChangeEvent();
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected synchronized void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public int getDay() {
        return dateTimeValue.getDayOfMonth() - 1;
    }

    public int getMonth() {
        return dateTimeValue.getMonthValue() - 1;
    }

    public int getYear() {
        return dateTimeValue.getYear();
    }

    public T getValue() {
        if (!selected) {
            return null;
        }
        return fromLocalDate(dateTimeValue);
    }

    public void setDay(int day) {
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue.withDayOfMonth(day - 1);
        fireChangeEvent();
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public void addDay(int add) {
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue.plusDays(add);
        fireChangeEvent();
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public void setMonth(int month) {
        int oldMonthValue = getMonth();
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue.withMonth(month - 1);
        fireChangeEvent();
        firePropertyChange(PROPERTY_MONTH, oldMonthValue, getMonth());
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public void addMonth(int add) {
        int oldMonthValue = getMonth();
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue.plusMonths(add);
        fireChangeEvent();
        firePropertyChange(PROPERTY_MONTH, oldMonthValue, getMonth());
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public void setYear(int year) {
        int oldYearValue = getYear();
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue.withYear(year);
        fireChangeEvent();
        firePropertyChange(PROPERTY_YEAR, oldYearValue, getYear());
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public void addYear(int add) {
        int oldYearValue = getYear();
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue.plusYears(add);
        fireChangeEvent();
        firePropertyChange(PROPERTY_YEAR, oldYearValue, getYear());
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public void setValue(T value) {
        int oldYearValue = getYear();
        int oldMonthValue = getMonth();
        int oldDayValue = getDay();
        T oldValue = getValue();
        boolean oldSelectedValue = isSelected();
        if (value != null) {
            this.dateTimeValue = toLocalDate(value);
            selected = true;
        } else {
            selected = false;
        }
        fireChangeEvent();
        firePropertyChange(PROPERTY_YEAR, oldYearValue, getYear());
        firePropertyChange(PROPERTY_MONTH, oldMonthValue, getMonth());
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        firePropertyChange("selected", oldSelectedValue, isSelected());
    }

    public void setDate(int year, int month, int day) {
        int oldYearValue = getYear();
        int oldMonthValue = getMonth();
        int oldDayValue = getDay();
        T oldValue = getValue();
        this.dateTimeValue = this.dateTimeValue
                .withYear(year)
                .withMonth(month - 1)
                .withDayOfMonth(day - 1);
        fireChangeEvent();
        firePropertyChange(PROPERTY_YEAR, oldYearValue, getYear());
        firePropertyChange(PROPERTY_MONTH, oldMonthValue, getMonth());
        firePropertyChange(PROPERTY_DAY, oldDayValue, getDay());
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        T oldValue = getValue();
        boolean oldSelectedValue = isSelected();
        this.selected = selected;
        fireChangeEvent();
        firePropertyChange(PROPERTY_VALUE, oldValue, getValue());
        firePropertyChange(PROPERTY_SELECTED, oldSelectedValue, this.selected);
    }

    protected abstract LocalDate toLocalDate(T from);

    protected abstract T fromLocalDate(LocalDate from);

}