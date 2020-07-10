package org.jdatepicker;

import java.util.Calendar;

/**
 * Selection of a range of dates (start and end date, possibly interrupted with constraints).
 */
public interface RangeSelectionModel extends SelectionModel {

    /**
     * Set the first date in the range.
     * @param c
     */
    void setFirstDate(Calendar c);

    /**
     * Get first chronologically selected date.
     * @return null if there is no date selected.
     */
    Calendar getFirstDate();

    /**
     * Clear the first date in the range.
     */
    void clearFirstDate();

    /**
     * Set the last date in the range.
     * @param c
     */
    void setLastDate(Calendar c);

    /**
     * Get last chronologically selected date.
     * @return null if there is no date selected.
     */
    Calendar getLastDate();

    /**
     * Clear the last date in the range.
     */
    void clearLastDate();

}
