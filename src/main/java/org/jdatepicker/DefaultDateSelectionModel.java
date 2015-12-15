package org.jdatepicker;

import org.jdatepicker.constraints.DateSelectionConstraint;

import java.util.*;

/**
 * Default implementation fo the DatePickerModel only supports the GregorianCalendar.
 */
public class DefaultDateSelectionModel implements DateSelectionModel {

    private final SortedSet<Calendar> selected;
    private final List<DateSelectionConstraint> constraints;
    private final List<DateSelectionModelListener> listeners;

    /**
     * Create a model without any date selected.
     */
    public DefaultDateSelectionModel() {
        selected = new TreeSet<Calendar>();
        constraints = new ArrayList<DateSelectionConstraint>();
        listeners = new ArrayList<DateSelectionModelListener>();
    }

    /**
     * Create a model with one date already selected.
     * @param date
     */
    public DefaultDateSelectionModel(final Calendar date) {
        this();
        selected.addAll(Arrays.asList(date));
    }

    /**
     * Create a model with multiple selected already selected.
     * @param dates
     */
    public DefaultDateSelectionModel(final Collection<Calendar> dates) {
        this();
        selected.addAll(dates);
    }

    public void replaceSelectedDates(final Collection<Calendar> newSelection) {
        for (Calendar date : newSelection) {
            if (!isDateSelectable(date)) {
                throw new IllegalArgumentException("A un-selectable date was passed, first check the date with isDateSelectable.");
            }
        }
        DateSelectionModelEvent event = new DateSelectionModelEvent(this, getSelectedDates(), newSelection);
        selected.clear();
        selected.addAll(newSelection);
        for (DateSelectionModelListener listener : listeners) {
            listener.selectionChanged(event);
        }
    }

    public void clearSelectedDates() {
        DateSelectionModelEvent event = new DateSelectionModelEvent(this, getSelectedDates(), null);
        selected.clear();
        for (DateSelectionModelListener listener : listeners) {
            listener.selectionChanged(event);
        }
    }

    public void addToSelectedDates(final Calendar date) {
        if (!isDateSelected(date)) {
            if (!isDateSelectable(date)) {
                throw new IllegalArgumentException("A un-selectable date was passed, first check the date with isDateSelectable.");
            }
            selected.add(date);
            for (DateSelectionModelListener listener : listeners) {
                listener.selectionChanged(new DateSelectionModelEvent(this, null, Arrays.asList(date)));
            }
        }
    }

    public void removeFromSelectedDates(final Calendar date) {
        if (isDateSelected(date)) {
            selected.remove(date);
            for (DateSelectionModelListener listener : listeners) {
                listener.selectionChanged(new DateSelectionModelEvent(this, Arrays.asList(date), null));
            }
        }
    }

    public SortedSet<Calendar> getSelectedDates() {
        return selected;
    }

    public Calendar getFirstSelectedDate() {
        return selected.size() > 0 ? selected.first() : null;
    }

    public Calendar getLastSelectedDate() {
        return selected.size() > 0 ? selected.last() : null;
    }

    public Calendar getValue() {
        if (selected.size() > 1) {
            throw new IllegalStateException("This model has more than one date selected, do not use this method to access the model.");
        }
        return selected.size() > 0 ? selected.first() : null;
    }

    public boolean isDateSelected(final Calendar c) {
        return selected.contains(c);
    }

    public int getSelectionCount() {
        return selected.size();
    }

    public void addDateSelectionConstraint(final DateSelectionConstraint c) {
        constraints.add(c);
    }

    public void removeDateSelectionConstraint(final DateSelectionConstraint c) {
        constraints.remove(c);
    }

    public boolean isDateSelectable(final Calendar c) {
        for (DateSelectionConstraint constraint : constraints) {
            if (!constraint.isValidSelection(c)) {
                return false;
            }
        }
        return true;
    }

    public void addDateSelectionModelListener(final DateSelectionModelListener l) {
        listeners.add(l);
    }

    public void removeDateSelectionModelListener(final DateSelectionModelListener l) {
        listeners.remove(l);
    }

}
