package org.jdatepicker;

import org.jdatepicker.constraints.DateSelectionConstraint;

import java.util.Calendar;
import java.util.Collection;
import java.util.SortedSet;

/**
 * Date picker model, represents what can and is currently selected. The JDatePicker and JDatePanel components will
 * interrogate this interface to display the appropriate values.
 *
 * This model doesn't explicitly define the various modes of date selection, but by it's nature it will support
 * scenarios such as:
 * <ul>
 *     <li>selection of a single date (just one single value)</li>
 *     <li>selection of a multiple dates (multiple discrete values)</li>
 *     <li>selection of a range of dates (start and end date, possibly interrupted with constraints)</li>
 * </ul>
 */
public interface DateSelectionModel {

    /**
     * Removes all selected dates and adds a new selection of dates.
     * @param c
     * @throws java.lang.IllegalArgumentException if any of the new dates violate the selection constraints.
     */
    void replaceSelectedDates(Collection<Calendar> c);

    /**
     * Removes all the selected dates.
     */
    void clearSelectedDates();

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
     * Get first chronologically selected date.
     * @return null if there is no date selected.
     */
    Calendar getFirstSelectedDate();

    /**
     * Get last chronologically selected date.
     * @return null if there is no date selected.
     */
    Calendar getLastSelectedDate();

    /**
     * Get the single selected value for this date selection.
     * @return
     */
    Calendar getValue();

    /**
     * Check if the particular date is selected.
     * @param c
     * @return
     */
    boolean isDateSelected(Calendar c);

    /**
     * Get the number of dates selected.
     * @return
     */
    int getSelectionCount();

    /**
     * Add a selection constraint. All constraints must indicate a valid selection for a date to be selectable.
     * @param c
     */
    void addDateSelectionConstraint(DateSelectionConstraint c);

    /**
     * Remove a selection constraint.
     */
    void removeDateSelectionConstraint(DateSelectionConstraint c);

    /**
     * Check if it is possible to select a date.
     * @param c
     * @return
     */
    boolean isDateSelectable(Calendar c);

    /**
     * Adds a listener to the list that is notified each time a change to the data model occurs.
     * @param l
     */
    void addDateSelectionModelListener(DateSelectionModelListener l);

    /**
     * Removes a listener from the list that is notified each time a change to the data model occurs.
     * @param l
     */
    void removeDateSelectionModelListener(DateSelectionModelListener l);

}
