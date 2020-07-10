package org.jdatepicker;

import java.util.Calendar;

/**
 * Selection of a single date (just one single value)
 */
public interface SingleSelectionModel extends SelectionModel {

    /**
     * Set the single date selected.
     * @param c
     */
    void setSelectedDate(Calendar c);

    /**
     * Get the single selected value for this date selection.
     * @return
     */
    Calendar getSelectedDate();

    /**
     * Clear the date selection.
     */
    void clearSelectedDate();

}
