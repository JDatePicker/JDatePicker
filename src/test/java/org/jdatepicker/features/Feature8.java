package org.jdatepicker.features;

import org.jdatepicker.ComponentFormatDefaults;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Change the date display formats.
 *
 * a. Change the date format of the month selector label and popup.
 * b. Change the date format of the day of week headers.
 * c. Change the date format of the today label.
 * d. Change the date format of the selected date.
 * e. Make the text area editable and allow the user to change the format of the edit field.
 */
public class Feature8 {

    public static void main(final String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Change the component defaults before instantiating
        ComponentFormatDefaults defaults = ComponentFormatDefaults.getInstance();

        defaults.setFormat(ComponentFormatDefaults.Key.TODAY_SELECTOR, new SimpleDateFormat("EEE dd MMM yy"));
        defaults.setFormat(ComponentFormatDefaults.Key.DOW_HEADER, new SimpleDateFormat("EEEE"));
        defaults.setFormat(ComponentFormatDefaults.Key.SELECTED_DATE_FIELD, new SimpleDateFormat("MM.dd.yy"));
        defaults.setFormat(ComponentFormatDefaults.Key.MONTH_SELECTOR, new SimpleDateFormat("MMM"));

        // Create the JDatePanel
        final JDatePanel datePanel = new JDatePanel(Calendar.getInstance());
        panel.add(datePanel);

        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker(Calendar.getInstance());
        datePicker.setTextEditable(true);
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

}
