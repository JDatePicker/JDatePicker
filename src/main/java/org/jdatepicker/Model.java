package org.jdatepicker;

import javax.swing.event.ChangeListener;

public interface Model<T> {

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

}
