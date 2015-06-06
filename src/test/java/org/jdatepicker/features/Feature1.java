package org.jdatepicker.features;

import org.jdatepicker.*;

import javax.swing.*;

/**
 * Simplest case for each one of the supported components.
 *
 * a. Construct a component by calling it's constructor, not having to use a an component factory.
 * b. The object constructed should extend JComponent, so it can be passed to a panel without the need for casting.
 * c. The component should be created with sensible defaults (no date selected).
 */
public class Feature1 {

    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel
        JDatePanel datePanel = new JDatePanel();
        panel.add(datePanel);

        // Create the JDatePicker
        JDatePicker datePicker = new JDatePicker();
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

}
