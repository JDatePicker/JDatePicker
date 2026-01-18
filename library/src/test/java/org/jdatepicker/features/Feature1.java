package org.jdatepicker.features;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Simplest case for each one of the supported components.
 *
 * a. Construct a component by calling it's constructor, not having to use a an component factory.
 * b. The object constructed should extend JComponent, so it can be passed to a panel without the need for casting.
 * c. The component should be created with sensible defaults (no date selected).
 * d. Read the value of the selected value, returns null if no date is selected.
 */
public class Feature1 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel
        final JDatePanel datePanel = new JDatePanel();
        panel.add(datePanel);

        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker();
        panel.add(datePicker);

        // Add a button to print out date value
        final JButton button = new JButton("Read Value");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // The default model is UtilCalendarModel
                Calendar c1 = (Calendar) datePanel.getModel().getValue();
                System.out.println("JDatePanel: " + (c1 != null ? sdf.format(c1.getTime()) : null));

                Calendar c2 = (Calendar) datePicker.getModel().getValue();
                System.out.println("JDatePicker: " + (c2 != null ? sdf.format(c2.getTime()) : null));
            }
        });
        panel.add(button);

        // Make the frame visible
        frame.setVisible(true);
    }

}
