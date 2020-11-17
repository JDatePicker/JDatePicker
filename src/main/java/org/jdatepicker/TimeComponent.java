package org.jdatepicker;

import org.jdatepicker.constraints.TimeSelectionConstraint;

/**
 * This interface is implemented by all components which represent a time.
 *
 * Created 12 November 2020
 *
 * @author Marc Jakobi
 */
public interface TimeComponent extends TemporalComponent {

    /**
     * Returns the model of the currently represented time in the component.
     *
     * @return the model
     */
    @Override
    TimeModel<?> getModel();

    /**
     * Adds an constraint on selectable times.
     *
     * @param constraint the constraint to add
     */
    void addTimeSelectionConstraint(TimeSelectionConstraint constraint);

    /**
     * Removes an constraint on selectable times.
     *
     * @param constraint the constraint to remove
     */
    void removeTimeSelectionConstraint(TimeSelectionConstraint constraint);

}
