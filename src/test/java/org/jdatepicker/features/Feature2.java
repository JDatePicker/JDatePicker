package org.jdatepicker.features;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.util.Calendar;

/**
 * Construct the components specifying some initial data in the constructor.
 *
 * a. Construct date models from java.util.Calendar, java.util.Date and java.sql.Date.
 */
public class Feature2 {

    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(650, 300);

        // Create a flow layout panel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel with a calendar
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1980);
        c.set(Calendar.MONTH, 4);
        c.set(Calendar.DATE, 1);
        JDatePanel datePanel = new JDatePanel(c);
        panel.add(datePanel);

        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 6);
        datePanel = new JDatePanel(new java.util.Date(c.getTimeInMillis()));
        panel.add(datePanel);

        c.set(Calendar.YEAR, 2014);
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 13);
        datePanel = new JDatePanel(new java.sql.Date(c.getTimeInMillis()));
        panel.add(datePanel);

        // Create the JDatePicker
        c.set(Calendar.YEAR, 1980);
        c.set(Calendar.MONTH, 4);
        c.set(Calendar.DATE, 1);
        JDatePicker datePicker = new JDatePicker(c);
        panel.add(datePicker);

        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 6);
        datePicker = new JDatePicker(new java.util.Date(c.getTimeInMillis()));
        panel.add(datePicker);

        c.set(Calendar.YEAR, 2014);
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 13);
        datePicker = new JDatePicker(new java.sql.Date(c.getTimeInMillis()));
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

}
