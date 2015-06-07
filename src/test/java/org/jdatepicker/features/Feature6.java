package org.jdatepicker.features;

import org.jdatepicker.ComponentTextDefaults;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.util.Locale;

/**
 * In some cases a locale may not be supported, in which case you will want to change the default texts for a widget.
 *
 * a. Set all the text variables, overriding even the months and the day of the week abbreviations.
 */
public class Feature6 {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // Create a frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Change the component defaults before instantiating
        ComponentTextDefaults defaults = ComponentTextDefaults.getInstance();
        defaults.setText(ComponentTextDefaults.Key.YEAR, "Jaar");
        defaults.setText(ComponentTextDefaults.Key.MONTH, "Maand");
        defaults.setText(ComponentTextDefaults.Key.TODAY, "Vandag");
        defaults.setText(ComponentTextDefaults.Key.CLEAR, "Vee uit");

        defaults.setText(ComponentTextDefaults.Key.JANUARY, "Januarie");
        defaults.setText(ComponentTextDefaults.Key.FEBRUARY, "Februarie");
        defaults.setText(ComponentTextDefaults.Key.MARCH, "Maart");
        defaults.setText(ComponentTextDefaults.Key.APRIL, "April");
        defaults.setText(ComponentTextDefaults.Key.MAY, "Mei");
        defaults.setText(ComponentTextDefaults.Key.JUNE, "Junie");
        defaults.setText(ComponentTextDefaults.Key.JULY, "Julie");
        defaults.setText(ComponentTextDefaults.Key.AUGUST, "Augustus");
        defaults.setText(ComponentTextDefaults.Key.SEPTEMBER, "September");
        defaults.setText(ComponentTextDefaults.Key.OCTOBER, "Oktober");
        defaults.setText(ComponentTextDefaults.Key.NOVEMBER, "November");
        defaults.setText(ComponentTextDefaults.Key.DECEMBER, "Desember");

        defaults.setText(ComponentTextDefaults.Key.SUN, "So");
        defaults.setText(ComponentTextDefaults.Key.MON, "Ma");
        defaults.setText(ComponentTextDefaults.Key.TUE, "Di");
        defaults.setText(ComponentTextDefaults.Key.WED, "Wo");
        defaults.setText(ComponentTextDefaults.Key.THU, "Do");
        defaults.setText(ComponentTextDefaults.Key.FRI, "Vr");
        defaults.setText(ComponentTextDefaults.Key.SAT, "Sa");

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
