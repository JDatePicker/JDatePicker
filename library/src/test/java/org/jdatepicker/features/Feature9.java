package org.jdatepicker.features;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Disable a the widget.
 *
 * a. Disable the widget and colors should be updated appropriately.
 * b. No values should be set when clicking on the component.
 */
public class Feature9 {

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
        datePanel.setEnabled(false);
        panel.add(datePanel);

        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker();
        datePicker.setEnabled(false);
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
