package org.jdatepicker.features;

import org.jdatepicker.*;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Construct components with a custom date model.
 *
 * a. Be able to create a component with a custom date model.
 * b. Monitor changes on the custom date model, only get one notification with change listener.
 *
 * NOTE: with the current structure which only supports java.util.Calendar this feature has no value.
 */
public class Feature3 {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(final String[] args) {
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel
        final JDatePanel datePanel1 = new JDatePanel(new DemoModel("1980-05-01"));
        datePanel1.getModel().addDateSelectionModelListener(new DateSelectionModelListener() {
            public void selectionChanged(DateSelectionModelEvent e) {
                SelectionModel model = e.getSource();
                System.out.println(String.format("%s", sdf.format(model.getValue())));
            }
        });
        panel.add(datePanel1);

        final JDatePanel datePanel2 = new JDatePanel(new DemoModel(null));
        datePanel2.getModel().addDateSelectionModelListener(new DateSelectionModelListener() {
            public void selectionChanged(DateSelectionModelEvent e) {
                SelectionModel model = e.getSource();
                System.out.println(String.format("%s", sdf.format(model.getValue())));
            }
        });
        panel.add(datePanel2);

        // Create the JDatePicker
        final JDatePicker datePicker1 = new JDatePicker(new DemoModel("1980-06-06"));
        datePicker1.getModel().addDateSelectionModelListener(new DateSelectionModelListener() {
            public void selectionChanged(DateSelectionModelEvent e) {
                SelectionModel model = e.getSource();
                System.out.println(String.format("%s", sdf.format(model.getValue())));
            }
        });
        panel.add(datePicker1);

        final JDatePicker datePicker2 = new JDatePicker(new DemoModel(null));
        datePicker2.getModel().addDateSelectionModelListener(new DateSelectionModelListener() {
            public void selectionChanged(DateSelectionModelEvent e) {
                SelectionModel model = e.getSource();
                System.out.println(String.format("%s", sdf.format(model.getValue())));
            }
        });
        panel.add(datePicker2);

        // Make the frame visible
        frame.setVisible(true);
    }

    public static class DemoModel extends AbstractSelectionModel {

        private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        public DemoModel(String value) {
            super(toCalendar(value));
        }

        private static Calendar toCalendar(String from) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(from));
                return c;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
