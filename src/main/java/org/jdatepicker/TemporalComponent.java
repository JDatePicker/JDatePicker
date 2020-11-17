package org.jdatepicker;

import java.awt.event.ActionListener;

/**
 * Parent interface for temporal components.
 *
 * Created 12 November 2020
 *
 * @author Marc Jakobi
 */
public interface TemporalComponent {

    /**
     * Adds an ActionListener. The actionListener is notified when a user clicks
     * on a date. Deliberately selecting a date will trigger this event, not
     * scrolling which fires a ChangeEvent for ChangeListeners.
     *
     * @param actionListener The listener to add
     */
    void addActionListener(ActionListener actionListener);

    /**
     * Removes the ActionListener. The actionListener is notified when a user
     * clicks on a date.
     *
     * @param actionListener The listener to remove
     */
    void removeActionListener(ActionListener actionListener);

    /**
     * @return the model
     */
    Model<?> getModel();
}
