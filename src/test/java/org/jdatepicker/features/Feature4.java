package org.jdatepicker.features;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import org.jdatepicker.JUtilDatePanel;
import org.jdatepicker.JUtilDatePicker;

/**
 * Change the LnF for the component.
 *
 * a. Change look and feel to platform specific look and feel.
 * b. Look and feel should change theme of component.
 */
public class Feature4 {

    public static void main(final String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // Change the look and feel to windows LnF
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel
        JDatePanel<?> datePanel = new JUtilDatePanel();
        panel.add(datePanel);

        // Create the JDatePicker
        JDatePicker<?> datePicker = new JUtilDatePicker();
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

}
