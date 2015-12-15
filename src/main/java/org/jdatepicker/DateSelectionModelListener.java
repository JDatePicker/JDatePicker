package org.jdatepicker;

import java.util.EventListener;

/**
 * Implement this interface to get notified of the selection change on the date picker component model.
 */
public interface DateSelectionModelListener extends EventListener {

    /**
     * Event gets fired whenever the date selection changes.
     * @param event
     */
    void selectionChanged(DateSelectionModelEvent event);

}
