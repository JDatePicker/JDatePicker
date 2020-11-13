package org.jdatepicker.constraints;

import org.jdatepicker.TimeModel;

/**
 * This interface provides a callback function to limit the selection of a time from the picker and panel.
 *
 * @author Marc Jakobi
 */
public interface TimeSelectionConstraint {

    /**
     * Check the models value to be a valid, selectable time.
     *
     * @param model The model to check
     * @return <code>true</code> if the models value is valid, else returns
     * <code>false</code>
     */
    boolean isValidSelection(TimeModel<?> model);

}
