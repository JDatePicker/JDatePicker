package org.jdatepicker.features;

import org.jdatepicker.ComponentColorDefaults;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.constraints.DateSelectionConstraint;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * Colour theme may be changed to match application style or the platform colours.
 *
 * a. Override all the colours for the widget.
 * b. Add a custom date selection constraint.
 * c. Be able to change the colours of the buttons.
 * d. Be able to change the colours of the icons.
 * e. Be able to change the colours of the JDatePicker text area and button.
 * f. Be able to change the colours of the month selection popup.
 * g. Simplified colour model.
 */
public class Feature7 {

    public static void main(final String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(550, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Change the component defaults before instantiating
        final ComponentColorDefaults defaults = ComponentColorDefaults.getInstance();

        defaults.setColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR, Color.ORANGE);
        defaults.setColor(ComponentColorDefaults.Key.BG_MONTH_SELECTOR, Color.BLACK);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_HEADER, Color.RED);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_HEADER, Color.GREEN);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_THIS_MONTH, Color.BLACK);
        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_OTHER_MONTH, Color.DARK_GRAY);
        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY, Color.YELLOW);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID, Color.LIGHT_GRAY);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_NOT_SELECTABLE, Color.RED);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_SELECTED, Color.BLACK);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_SELECTED, Color.YELLOW);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED, Color.RED);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_TODAY_SELECTED, Color.YELLOW);

        defaults.setColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_ENABLED, Color.PINK);
        defaults.setColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_DISABLED, Color.YELLOW);
        defaults.setColor(ComponentColorDefaults.Key.BG_TODAY_SELECTOR, Color.BLACK);

        defaults.setColor(ComponentColorDefaults.Key.POPUP_BORDER, Color.WHITE);

        // Create the JDatePanel
        final JDatePanel datePanel = new JDatePanel(Calendar.getInstance());
        datePanel.addDateSelectionConstraint(new DateSelectionConstraint() {
            public boolean isValidSelection(DateModel<?> model) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -1);
                return !(c.get(Calendar.YEAR) == model.getYear() &&
                        c.get(Calendar.MONTH) == model.getMonth() &&
                        c.get(Calendar.DATE) == model.getDay());
            }
        });
        panel.add(datePanel);

        // Create the JDatePicker
        final JDatePicker datePicker = new JDatePicker(Calendar.getInstance());
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

}
