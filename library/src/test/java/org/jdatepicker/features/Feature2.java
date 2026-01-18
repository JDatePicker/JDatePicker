package org.jdatepicker.features;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

/**
 * Construct the components specifying some initial data in the constructor.
 *
 * a. Construct date models from java.util.Calendar, java.util.Date and java.sql.Date.
 * b. Get notified of different property changes.
 */
public class Feature2 {

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(850, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel with a calendar
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1980);
        c.set(Calendar.MONTH, 4);
        c.set(Calendar.DATE, 1);
        final JDatePanel datePanel1 = new JDatePanel(c);
        datePanel1.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePanel1);

        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 6);
        final JDatePanel datePanel2 = new JDatePanel(new java.util.Date(c.getTimeInMillis()));
        datePanel2.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePanel2);

        c.set(Calendar.YEAR, 2014);
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 13);
        final JDatePanel datePanel3 = new JDatePanel(new java.sql.Date(c.getTimeInMillis()));
        datePanel3.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePanel3);

        final JDatePanel datePanel4 = new JDatePanel();
        datePanel4.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePanel4);

        // Create the JDatePicker
        c.set(Calendar.YEAR, 1980);
        c.set(Calendar.MONTH, 4);
        c.set(Calendar.DATE, 1);
        final JDatePicker datePicker1 = new JDatePicker(c);
        datePicker1.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePicker1);

        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 6);
        final JDatePicker datePicker2= new JDatePicker(new java.util.Date(c.getTimeInMillis()));
        datePicker2.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePicker2);

        c.set(Calendar.YEAR, 2014);
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 13);
        final JDatePicker datePicker3 = new JDatePicker(new java.sql.Date(c.getTimeInMillis()));
        datePicker3.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePicker3);

        final JDatePicker datePicker4 = new JDatePicker();
        datePicker4.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println(String.format("%s: %s -> %s", e.getPropertyName(), e.getOldValue(), e.getNewValue()));
            }
        });
        panel.add(datePicker4);

        // Make the frame visible
        frame.setVisible(true);
    }

}
