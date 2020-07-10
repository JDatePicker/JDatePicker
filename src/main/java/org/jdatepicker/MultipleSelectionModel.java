package org.jdatepicker;

import java.util.Calendar;
import java.util.Collection;
import java.util.SortedSet;

/**
 * Selection of a multiple dates (multiple discrete values).
 */
public interface MultipleSelectionModel extends SelectionModel {

    /**
     * Removes all selected dates and adds a new selection of dates.
     * @param c
     * @throws java.lang.IllegalArgumentException if any of the new dates violate the selection constraints.
     */
    void replaceSelectedDates(Collection<Calendar> c);

    /**
     * Add a selected date to the component.
     * @param c
     * @throws java.lang.IllegalArgumentException if the added date violates the selection constraints.
     */
    void addToSelectedDates(Calendar c);

    /**
     * Remove a selected date from the component.
     * @param c
     */
    void removeFromSelectedDates(Calendar c);

    /**
     * Get all the dates which are selected.
     * @return
     */
    SortedSet<Calendar> getSelectedDates();

    /**
     * Removes all the selected dates.
     */
    void clearSelectedDates();

}
