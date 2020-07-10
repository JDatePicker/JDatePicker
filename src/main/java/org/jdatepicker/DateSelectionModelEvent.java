package org.jdatepicker;

import java.io.Serializable;
import java.util.*;

/**
 * Event which represents a single change to the date selection of the DatePicker component.
 */
public final class DateSelectionModelEvent extends EventObject implements Serializable {

    private final SortedSet<Calendar> unselected;
    private final SortedSet<Calendar> selected;

    /**
     * Constructs an event object for the selection change.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DateSelectionModelEvent(final SelectionModel source, final Collection<Calendar> unselected, final Collection<Calendar> selected) {
        super(source);
        this.unselected = unselected != null ? new TreeSet<Calendar>(unselected) : new TreeSet<Calendar>();
        this.selected = selected != null ? new TreeSet<Calendar>(selected) : new TreeSet<Calendar>();
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    public SelectionModel getSource() {
        return (SelectionModel) source;
    }

    /**
     * Get a list of all the dates which were unselected with this change.
     *
     * @return
     */
    public SortedSet<Calendar> getUnselected() {
        return unselected;
    }

    /**
     * Get a list of all the dates which were selected with this change.
     *
     * @return
     */
    public SortedSet<Calendar> getSelected() {
        return selected;
    }

}
