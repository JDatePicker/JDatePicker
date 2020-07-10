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
 * various selection modes such as Single Selection, Date Range Selection and Multiple Date Selection.
 */
public interface SelectionModel {

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
