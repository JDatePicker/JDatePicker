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

/**
 * Generic date model interface for JDatePicker components.
 *
 * <p>This interface defines the contract for date models that store and manipulate
 * dates in the Gregorian calendar system. It supports various date types through generics,
 * including {@link java.util.Date}, {@link java.util.Calendar}, and {@link java.sql.Date}.
 *
 * <p><b>Event Notification:</b>
 * <ul>
 *   <li>{@link javax.swing.event.ChangeListener} - Notified when any date component changes
 *       (year, month, day). Fired during user interaction with the calendar.</li>
 *   <li>{@link java.beans.PropertyChangeListener} - Notified when properties change, following
 *       JavaBeans conventions. Used for data binding and validation frameworks.</li>
 * </ul>
 *
 * <p><b>Month Indexing:</b> Months are 0-indexed following {@link java.util.Calendar} convention:
 * <ul>
 *   <li>0 = January</li>
 *   <li>1 = February</li>
 *   <li>...</li>
 *   <li>11 = December</li>
 * </ul>
 *
 * <p><b>Selection State:</b> The model maintains a "selected" state separate from the date value.
 * This allows distinguishing between "no date selected" and "a specific date selected". When
 * {@code isSelected()} returns false, the getValue() returns null.
 *
 * <p><b>Thread Safety:</b> Implementations use {@link java.util.concurrent.CopyOnWriteArraySet}
 * for listener management, making listener operations thread-safe. However, date manipulation
 * methods are not synchronized and should be called from the Event Dispatch Thread when used
 * in Swing components.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * DateModel&lt;Calendar&gt; model = new UtilCalendarModel();
 * model.setDate(2024, 0, 15); // January 15, 2024
 * model.setSelected(true);
 *
 * model.addChangeListener(e -&gt; {
 *     System.out.println("Date changed: " + model.getValue());
 * });
 * </pre>
 *
 * @param <T> The type of date object this model wraps (e.g., java.util.Date, java.util.Calendar, java.sql.Date)
 * @author Juan Heyns
 * @since 1.0
 * @see UtilDateModel
 * @see UtilCalendarModel
 * @see SqlDateModel
 */
public interface DateModel<T> {

    /**
     * Adds a ChangeListener. ChangeListeners will be notified when the internal
     * state of the control changes. This means that as a user scrolls through
     * dates the internal model changes, which fires a ChangeEvent each time it
     * changes.
     *
     * @param changeListener The changelistener to add.
     */
    void addChangeListener(ChangeListener changeListener);

    /**
     * Removes the specified ChangeListener. ChangeListeners will be notified
     * when the selected date is changed.
     *
     * @param changeListener The changelistener to remove.
     */
    void removeChangeListener(ChangeListener changeListener);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @return year
     */
    int getYear();

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param year year
     */
    void setYear(int year);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @return month
     */
    int getMonth();

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param month month
     */
    void setMonth(int month);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @return day
     */
    int getDay();

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param day day
     */
    void setDay(int day);

    /**
     * Getters and setters which represent a gregorian date.
     *
     * @param year  year
     * @param month month
     * @param day   day
     */
    void setDate(int year, int month, int day);

    /**
     * Add or substract number of years.
     *
     * @param add years
     */
    void addYear(int add);

    /**
     * Add or substract number of months.
     *
     * @param add months
     */
    void addMonth(int add);

    /**
     * Add or substract number of day.
     *
     * @param add days
     */
    void addDay(int add);

    /**
     * Get the value this model represents.
     *
     * @return current value
     */
    T getValue();

    /**
     * Set the value this model represents.
     *
     * @param value new value
     */
    void setValue(T value);

    /**
     * @return Is the value selected or is it not.
     */
    boolean isSelected();

    /**
     * Set the value as selected.
     *
     * @param selected select this value?
     */
    void setSelected(boolean selected);

    /**
     * Adds a PropertyChangeListener to the list of bean listeners.
     * The listener is registered for all bound properties of the target bean.
     *
     * @param listener The PropertyChangeListener to be added
     * @see #removePropertyChangeListener(PropertyChangeListener)
     */
    void addPropertyChangeListener(PropertyChangeListener listener);


    /**
     * Removes a PropertyChangeListener from the list of bean listeners.
     * This method should be used to remove PropertyChangeListeners that
     * were registered for all bound properties of the target bean.
     *
     * @param listener The PropertyChangeListener to be removed
     * @see #addPropertyChangeListener(PropertyChangeListener)
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

}
