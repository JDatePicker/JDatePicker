package org.jdatepicker.features;

import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Construct components with a custom date model.
 *
 * a. Be able to create a component with a custom date model.
 * b. Monitor changes on the custom date model, only get one notification with change listener.
 */
public class Feature3 {

    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);

        // Create a flow layout panel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Create the JDatePanel
        JDatePanel datePanel = new JDatePanel(new DemoDateModel("1980-05-01"));
        datePanel.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DemoDateModel source = (DemoDateModel) e.getSource();
                System.out.println(source.getValue());
            }
        });
        panel.add(datePanel);

        datePanel = new JDatePanel(new DemoDateModel(null));
        datePanel.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DemoDateModel source = (DemoDateModel) e.getSource();
                System.out.println(source.getValue());
            }
        });
        panel.add(datePanel);

        // Create the JDatePicker
        JDatePicker datePicker = new JDatePicker(new DemoDateModel("1980-06-06"));
        datePicker.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DemoDateModel source = (DemoDateModel) e.getSource();
                System.out.println(source.getValue());
            }
        });
        panel.add(datePicker);

        datePicker = new JDatePicker(new DemoDateModel(null));
        datePicker.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DemoDateModel source = (DemoDateModel) e.getSource();
                System.out.println(source.getValue());
            }
        });
        panel.add(datePicker);

        // Make the frame visible
        frame.setVisible(true);
    }

    public static class DemoDateModel extends AbstractDateModel<String> {

        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        public DemoDateModel(String value) {
            super();
            setValue(value);
        }

        @Override
        protected String fromCalendar(Calendar from) {
            return sdf.format(from.getTime());
        }

        @Override
        protected Calendar toCalendar(String from) {
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
